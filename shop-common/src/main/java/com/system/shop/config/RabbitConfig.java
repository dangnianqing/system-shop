package com.system.shop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class RabbitConfig {

    /**
     * 队列名称
     */


    public static final String order_status_queue = "order_status_queue";
    public static final String product_push_queue = "product_push_queue";


    /**
     * 设置路由key
     */


    public static final String order_status_queue_key = "order_status_queue";
    public static final String product_push_queue_key = "product_push_queue";

    /**
     * 交换空间名称(点对点)
     */
    public static final String DIRECT_EXCHANGE = "exchange.direct";
    /**
     * 交换空间名称(广播)
     */
    public static final String FANOUT_EXCHANGE = "exchange.fanout";

    /**
     * 声明队列名称(点对点)
     *
     * @return
     */
    @Bean
    public Queue orderStatusQueue() {
        return new Queue(order_status_queue, true);
    }

    @Bean
    public Queue productPushQueue() {
        return new Queue(product_push_queue, true);
    }


    /**
     * 将队列通过路由key到绑定交互机上(点对点)
     *
     * @param
     * @return
     */
    @Bean
    public Binding bindingExchangeOrderStatusQueue(DirectExchange exchange, @Qualifier("orderStatusQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(order_status_queue_key);
    }

    @Bean
    public Binding bindingExchangeProductPushQueue(DirectExchange exchange, @Qualifier("productPushQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(product_push_queue_key);
    }


    /**
     * 点对点模式
     *
     * @return
     */
    @Bean
    public DirectExchange getDirectExchange() {
        return new DirectExchange(DIRECT_EXCHANGE, true, false);
    }

    /**
     * 广播模式
     *
     * @return
     */
    @Bean
    public FanoutExchange getFanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE, true, false);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 设置开启Mandatory，才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        //https://cloud.tencent.com/developer/article/2384925
        rabbitTemplate.setMandatory(true);
        // 交换机收到消息回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause));
        // 队列收到消息回调，如果失败的话会进行 returnCallback 的回调处理，反之成功就不会回调。
        rabbitTemplate.setReturnsCallback(returned -> {
            log.info("returnCallback:     " + "消息：" + returned.getMessage());
            log.info("returnCallback:     " + "回应码：" + returned.getReplyCode());
            log.info("returnCallback:     " + "回应信息：" + returned.getReplyText());
            log.info("returnCallback:     " + "交换机：" + returned.getExchange());
            log.info("returnCallback:     " + "路由键：" + returned.getRoutingKey());
        });

        return rabbitTemplate;
    }
}

