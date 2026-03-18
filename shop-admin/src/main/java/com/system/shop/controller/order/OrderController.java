package com.system.shop.controller.order;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.system.shop.common.config.RabbitConfig;
import com.system.shop.common.exception.BaseException;
import com.system.shop.common.Result;
import com.system.shop.common.ResultEnum;
import com.system.shop.common.utils.SnowFlake;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.Order;
import com.system.shop.entity.OrderLog;
import com.system.shop.bean.search.OrderSearch;
import com.system.shop.service.OrderLogService;
import com.system.shop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/4/22 15:35
 * @Description：
 */

@RequestMapping("/order")
@RestController
@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderLogService orderLogService;

    @PostMapping("/selectList")
    public Result<PageInfo<Order>> list(@RequestBody OrderSearch orderSearch) {
        return Result.success(orderService.findPage(orderSearch));
    }


    @GetMapping("/selectOrder")
    public Result<Order> selectOrder(String orderCode) {
        if (StringUtils.isEmpty(orderCode)) {
            throw new BaseException(ResultEnum.PARAM_ERROR, "订单编号为空");
        }
        return Result.success(orderService.selectByOrderCode(orderCode));
    }


    @GetMapping("/selectOrderLogs")
    public Result<List<OrderLog>> selectOrderLogs(String orderCode) {
        if (StringUtils.isEmpty(orderCode)) {
            throw new BaseException(ResultEnum.PARAM_ERROR, "订单编号为空");
        }
        return Result.success(orderLogService.selectByOrderCode(orderCode));
    }


    @PostMapping("/saveAddress")
    public Result<Boolean> updateAddress(@RequestBody Order order) {
        if (StringUtils.isEmpty(order.getOrderCode())) {
            throw new BaseException(ResultEnum.PARAM_ERROR, "订单编号为空");
        }
        return Result.success(orderService.updateAddress(order));
    }

    @GetMapping("/payOrder")
    public Result<Boolean> payOrder(String orderCode, HttpServletRequest request) {
        return Result.success(orderService.updatePayOrder(orderCode, this.getOperator(request)));
    }
    @PostMapping("/savePayPrice")
    public Result<Boolean> savePayPrice(@RequestBody Order order, HttpServletRequest request) {
        if (StringUtils.isEmpty(order.getOrderCode())) {
            throw new BaseException(ResultEnum.PARAM_ERROR, "订单编号为空");
        }
        return Result.success(orderService.updatePayPrice(order, getOperator(request)));
    }


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendmq")
    public Result<Boolean> sendMq(String orderCode) {
        if (StringUtils.isEmpty(orderCode)) {
            throw new BaseException(ResultEnum.PARAM_ERROR, "订单编号为空");
        }
        Order order = orderService.selectByOrderCode(orderCode);
// 1. 创建消息ID，确认机制发送消息时，需要给每个消息设置一个全局唯一 id，以区分不同消息，避免 ack 冲突
        CorrelationData correlationData = new CorrelationData(SnowFlake.getIdString());
        Message message = MessageBuilder
                .withBody(JSON.toJSONString(order).getBytes(StandardCharsets.UTF_8))
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        // 3. 发送消息到 RabbitMQ 服务器，需要指定交换机、路由键、消息载体以及消息ID
        rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE, RabbitConfig.order_status_queue_key, message, correlationData);
        return Result.success(true);
    }


}
