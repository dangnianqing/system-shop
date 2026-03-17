package com.system.shop.enumer;

/**
 * 评论状态
 */
public enum CommentStatus {
    /**
     * 新订单，不能进行评论
     */
    NEW("新订单，不能进行评论"),
    /**
     * 未完成的评论
     */
    UNFINISHED("未完成评论"),

    /**
     * 待追评的评论信息
     */
    WAIT_CHASE("待追评评论"),

    /**
     * 已经完成评论
     */
    FINISHED("已经完成评论");


    private final String description;

    CommentStatus(String description) {
        this.description = description;
    }

    public String getDesc() {
        return this.description;
    }
}
