package com.example.systemshop.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * Redis 分布式锁（基于 StringRedisTemplate）。
 * - 获取锁默认最多等待 500ms，期间轮询尝试获取
 * - 锁到期时间由 ttlMs 控制
 * - 释放锁使用 Lua 脚本保证原子性，仅持有者可释放
 * - 支持 Watchdog 自动续约：未释放期间自动延长锁过期时间
 */
@Component
public class RedisDistributedLock {

    private static final long DEFAULT_WAIT_MS = 500L;
    private static final long DEFAULT_RETRY_SLEEP_MS = 20L;

    private static final String UNLOCK_LUA =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "  return redis.call('del', KEYS[1]) " +
            "else " +
            "  return 0 " +
            "end";

    private static final String RENEW_LUA =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "  return redis.call('pexpire', KEYS[1], ARGV[2]) " +
            "else " +
            "  return 0 " +
            "end";

    private final StringRedisTemplate stringRedisTemplate;

    // 续约调度器（守护线程）
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, r -> {
        Thread t = new Thread(r, "redis-lock-renewal");
        t.setDaemon(true);
        return t;
    });

    // 记录每个 key 的续约任务
    private final ConcurrentHashMap<String, ScheduledFuture<?>> renewTasks = new ConcurrentHashMap<>();

    public RedisDistributedLock(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 尝试加锁，最多等待 500ms。
     *
     * @param key   锁键
     * @param value 锁值（建议传调用方生成的唯一标识，若传 null 则内部自动生成）
     * @param ttlMs 锁过期毫秒
     * @return 加锁是否成功；若成功且 value 为 null，返回值可通过 {@link #currentLockValue()} 获取
     */
    public boolean tryLock(String key, String value, long ttlMs) {
        return tryLock(key, value, ttlMs, DEFAULT_WAIT_MS);
    }

    /**
     * 尝试加锁并开启自动续约，最多等待 500ms。
     */
    public boolean tryLockWithAutoRenew(String key, String value, long ttlMs) {
        boolean locked = tryLock(key, value, ttlMs, DEFAULT_WAIT_MS);
        if (locked) {
            String v = value != null ? value : currentLockValue();
            startAutoRenew(key, v, ttlMs);
        }
        return locked;
    }

    /**
     * 尝试加锁，最多等待 waitMs。
     *
     * @param key    锁键
     * @param value  锁值（建议传调用方生成的唯一标识，若传 null 则内部自动生成）
     * @param ttlMs  锁过期毫秒
     * @param waitMs 最长等待毫秒（示例为 500ms）
     * @return 是否加锁成功
     */
    public boolean tryLock(String key, String value, long ttlMs, long waitMs) {
        Objects.requireNonNull(key, "lock key must not be null");
        if (ttlMs <= 0) {
            throw new IllegalArgumentException("ttlMs must be > 0");
        }
        if (waitMs < 0) {
            throw new IllegalArgumentException("waitMs must be >= 0");
        }

        String lockValue = value != null ? value : UUID.randomUUID().toString();
        LOCK_VALUE_HOLDER.set(lockValue);

        long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(waitMs);
        do {
            Boolean ok = stringRedisTemplate.opsForValue()
                    .setIfAbsent(key, lockValue, Duration.ofMillis(ttlMs));
            if (Boolean.TRUE.equals(ok)) {
                return true;
            }
            sleepQuietly(DEFAULT_RETRY_SLEEP_MS);
        } while (System.nanoTime() < deadline);

        // 超时仍未获取到锁
        // 清理线程副本中的 lockValue，避免误用
        LOCK_VALUE_HOLDER.remove();
        return false;
    }

    /**
     * 释放锁。仅当传入 value 与 Redis 中存储的值一致时才会删除锁。
     * 同时会停止自动续约任务。
     *
     * @param key   锁键
     * @param value 锁值（必须与加锁时使用的 value 相同）
     * @return 是否释放成功
     */
    public boolean unlock(String key, String value) {
        Objects.requireNonNull(key, "lock key must not be null");
        Objects.requireNonNull(value, "lock value must not be null");

        stopAutoRenew(key);

        DefaultRedisScript<Long> script = new DefaultRedisScript<>(UNLOCK_LUA, Long.class);
        Long result = stringRedisTemplate.execute(script, Collections.singletonList(key), value);
        return result != null && result > 0L;
    }

    /**
     * 停止某个 key 的自动续约。
     */
    public void stopAutoRenew(String key) {
        ScheduledFuture<?> f = renewTasks.remove(key);
        if (f != null) {
            f.cancel(false);
        }
    }

    /**
     * 便利方法：使用锁包裹执行一段逻辑（无自动续约）。
     * 成功获取锁则执行 supplier，并在结束后释放锁；否则返回 fallback。
     */
    public <T> T executeWithLock(String key, String value, long ttlMs, Supplier<T> supplier, Supplier<T> fallback) {
        boolean locked = tryLock(key, value, ttlMs, DEFAULT_WAIT_MS);
        if (!locked) {
            return fallback != null ? fallback.get() : null;
        }
        try {
            return supplier.get();
        } finally {
            unlock(key, value != null ? value : currentLockValue());
        }
    }

    /**
     * 便利方法：使用锁包裹执行一段逻辑（自动续约）。
     */
    public <T> T executeWithLockAutoRenew(String key, String value, long ttlMs, Supplier<T> supplier, Supplier<T> fallback) {
        boolean locked = tryLockWithAutoRenew(key, value, ttlMs);
        if (!locked) {
            return fallback != null ? fallback.get() : null;
        }
        String v = value != null ? value : currentLockValue();
        try {
            return supplier.get();
        } finally {
            unlock(key, v);
        }
    }

    /**
     * 获取当前线程在最近一次 tryLock 中使用/生成的锁值。
     */
    public String currentLockValue() {
        return LOCK_VALUE_HOLDER.get();
    }

    private static final ThreadLocal<String> LOCK_VALUE_HOLDER = new ThreadLocal<>();

    private void startAutoRenew(String key, String value, long ttlMs) {
        // 每次调度周期：ttl 的一半，至少 200ms
        long periodMs = Math.max(200L, ttlMs / 2);
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(RENEW_LUA, Long.class);

        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {
            try {
                Long r = stringRedisTemplate.execute(
                        script,
                        Collections.singletonList(key),
                        value,
                        String.valueOf(ttlMs)
                );
                // 返回 0 表示非持有者或 key 已不存在，停止续约
                if (r == null || r == 0L) {
                    stopAutoRenew(key);
                }
            } catch (Exception ignored) {
                // 续约失败不抛出，下一次继续尝试；若键已不属于当前持有者会在上方被停止
            }
        }, periodMs, periodMs, TimeUnit.MILLISECONDS);

        // 覆盖已有任务（如有）
        ScheduledFuture<?> old = renewTasks.put(key, future);
        if (old != null) {
            old.cancel(false);
        }
    }

    private static void sleepQuietly(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 