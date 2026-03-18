package com.system.shop.controller.product;

import com.alibaba.fastjson.JSON;
import com.system.shop.bean.mq.OrderState;
import com.system.shop.common.Result;
import com.system.shop.config.RabbitConfig;
import com.system.shop.entity.Store;
import com.system.shop.enumer.OrderStatus;
import com.system.shop.index.ProductIndex;
import com.system.shop.index.ProductIndexSearch;
import com.system.shop.service.ProductElasticSearchService;
import com.system.shop.service.StoreService;
import com.system.shop.utils.SnowFlake;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/8 14:05
 * @Description：
 */
@RequestMapping("/product")
@RestController
public class ProductController {
    @Autowired
    private ProductElasticSearchService productElasticSearchService;
    @Autowired
    private StoreService storeService;

    @PostMapping("/searchPage")
    public Result<Page<ProductIndex>> searchPage(@RequestBody ProductIndexSearch productIndexSearch) {
        return Result.success(productElasticSearchService.searchPage(productIndexSearch));
    }


    @PostMapping("/searchByProductId/{productId}")
    public Result<ProductIndex> searchByProductIdStoreId(@PathVariable String productId) {
        return Result.success(productElasticSearchService.searchByProductId(productId));
    }

    @GetMapping("/selectStore/{id}")
    public Result<Store> selectStore(@PathVariable Long id) {
        return Result.success(storeService.selectById(id));
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/endMsg")
    public Result<Boolean> endMsg() {
        OrderState orderState = new OrderState("223232", OrderStatus.COMPLETE.name());
        // 1. 创建消息ID，确认机制发送消息时，需要给每个消息设置一个全局唯一 id，以区分不同消息，避免 ack 冲突
        CorrelationData correlationData = new CorrelationData(SnowFlake.getIdString());
        Message message = MessageBuilder
                .withBody(JSON.toJSONString(orderState).getBytes(StandardCharsets.UTF_8))
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        // 3. 发送消息到 RabbitMQ 服务器，需要指定交换机、路由键、消息载体以及消息ID
        rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE, RabbitConfig.order_status_queue_key, message, correlationData);
        return Result.success(Boolean.TRUE);
    }


}
