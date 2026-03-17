package com.system.shop.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.shop.base.ServiceImpl;
import com.system.shop.bean.Operator;
import com.system.shop.bean.mq.OrderState;
import com.system.shop.bean.order.OrderTagInfo;
import com.system.shop.bean.search.OrderSearch;
import com.system.shop.common.ResultCode;
import com.system.shop.config.RabbitConfig;
import com.system.shop.enumer.OrderStatus;
import com.system.shop.enumer.PayMethod;
import com.system.shop.enumer.PayStatus;
import com.system.shop.exception.BusinessException;
import com.system.shop.mapper.OrderMapper;
import com.system.shop.entity.Order;
import com.system.shop.entity.OrderLog;
import com.system.shop.service.MemberService;
import com.system.shop.service.OrderItemService;
import com.system.shop.service.OrderLogService;
import com.system.shop.service.OrderLogisticsService;
import com.system.shop.service.OrderService;
import com.system.shop.utils.SnowFlake;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderLogService orderLogService;
    @Autowired
    private OrderLogisticsService orderLogisticsService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MemberService memberService;

    @Override
    public PageInfo<Order> findPage(OrderSearch orderSearch) {
        PageHelper.startPage(orderSearch.getPageNum(), orderSearch.getPageSize());
        return new PageInfo(baseMapper.findPage(orderSearch.querymap()));
    }

    @Override
    public Order selectByOrderCode(String orderCode) {
        Order order = baseMapper.selectByOrderCode(orderCode);
        order.setOrderItems(orderItemService.selectByOrderCode(order.getOrderCode()));
        //如果是待支付状态计算 取消倒计时
        if (order.getOrderStatus().equals(OrderStatus.PENDING_PAYMENT.name())) {
            DateTime orderTime = DateUtil.offsetDay(order.getOrderTime(), 1);
            long countDownTime = orderTime.between(new Date(), DateUnit.MS);
            order.setCountDownTime(countDownTime);
        }
        return order;
    }

    @Override
    public Order selectOrderCode(String orderCode) {
        Order order = baseMapper.selectByOrderCode(orderCode);
        return order;
    }

    @Override
    public Boolean updateOrderCancel(String orderCode, Integer cancel) {
        Order order = baseMapper.selectByOrderCode(orderCode);
        if (order.getOrderStatus().equals(OrderStatus.PENDING_DELIVERY.name())) {
            Order bean = new Order();
            bean.setId(order.getId());
            bean.setCancel(cancel);
            return this.updateByIdSelective(bean);
        }
        throw new BusinessException(ResultCode.DATA_CHECK_ERROR, "只有待发货状态的订单才可修改取消状态");
    }

    @Override
    public Boolean updateAddress(Order order) {
        Order bean = baseMapper.selectByOrderCode(order.getOrderCode());
        if (bean == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "订单不存在");
        }
        order.setId(bean.getId());
        return this.updateByIdSelective(order);
    }


    @Override
    public Boolean updatePayPrice(Order order, Operator operator) {
        Order bean = baseMapper.selectByOrderCode(order.getOrderCode());
        if (bean == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "订单不存在");
        }
        order.setId(bean.getId());
        OrderLog orderLog = new OrderLog(order.getOrderCode(), "订单[" + order.getOrderCode() + "]修改支付金额，修改后的金额为：" + order.getPayPrice(), operator);
        this.updateByIdSelective(order);
        return orderLogService.insert(orderLog);
    }

    @Override
    public List<OrderTagInfo> selectOrderTagInfo(Long memberId) {
        return baseMapper.selectOrderTagInfo(memberId);
    }

    @Override
    public Boolean saveOrderLogistics(Order order, Operator operator) {
        Order bean = baseMapper.selectByOrderCode(order.getOrderCode());
        if (bean == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "订单不存在");
        }
        if (!bean.getOrderStatus().equals(OrderStatus.PENDING_DELIVERY.name())) {
            throw new BusinessException(ResultCode.DATA_CHECK_ERROR, "订单状态不正确");
        }
        orderLogisticsService.update(order.getOrderCode(), order.getOrderLogisticsList());
        order.setOrderStatus(OrderStatus.PENDING_RECEIPT.name());
        order.setId(bean.getId());
        this.updateByIdSelective(order);
        this.sendMqMsg(order.getOrderCode(), OrderStatus.PENDING_RECEIPT.name());
        OrderLog orderLog = new OrderLog(order.getOrderCode(), "订单[" + order.getOrderCode() + "]已发货，有" + order.getOrderLogisticsList().size() + "个快递单号", operator);
        return orderLogService.insert(orderLog);
    }

    @Override
    public Boolean updateCompleteOrder(String orderCode, Operator operator) {
        Order order = baseMapper.selectByOrderCode(orderCode);
        if (order == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "订单不存在");
        }
        Order bean = new Order();
        bean.setId(order.getId());
        bean.setOrderStatus(OrderStatus.COMPLETE.name());
        this.updateByIdSelective(bean);
        this.sendMqMsg(order.getOrderCode(), OrderStatus.COMPLETE.name());
        OrderLog orderLog = new OrderLog(order.getOrderCode(), "订单[" + order.getOrderCode() + "]已完成", operator);
        return orderLogService.insert(orderLog);
    }

    @Override
    public Boolean updateCancelOrder(String orderCode, Operator operator) {
        Order order = baseMapper.selectByOrderCode(orderCode);
        if (order.getCancel() == 0) {
            throw new BusinessException(ResultCode.DATA_CHECK_ERROR, "订单不可取消");
        }
        if (order.getOrderStatus().equals(OrderStatus.CANCELLED.name())) {
            throw new BusinessException(ResultCode.DATA_CHECK_ERROR, "订单已取消");
        }
        Order bean = new Order();
        bean.setId(order.getId());
        bean.setOrderStatus(OrderStatus.CANCELLED.name());
        bean.setCancelTime(new Date());
        this.updateByIdSelective(bean);
        OrderLog orderLog = new OrderLog(order.getOrderCode(), "订单[" + order.getOrderCode() + "]已取消", operator);
        this.sendMqMsg(order.getOrderCode(), OrderStatus.CANCELLED.name());
        return orderLogService.insert(orderLog);
    }

    @Override
    @Transactional
    public Boolean updatePayOrder(String orderCode, Operator operator) {
        Order bean = baseMapper.selectByOrderCode(orderCode);
        if (bean == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "订单不存在");
        }
        if (bean.getPayStatus().equals(PayStatus.PAID.name())) {
            throw new BusinessException(ResultCode.DATA_CHECK_ERROR, "订单已支付");
        }
        if (bean.getOrderStatus().equals(OrderStatus.CANCELLED.name())) {
            throw new BusinessException(ResultCode.DATA_CHECK_ERROR, "订单已取消");
        }
        if (bean.getPayStatus().equals(PayStatus.UNPAID.name()) && bean.getOrderStatus().equals(OrderStatus.PENDING_PAYMENT.name())) {
            Order order = new Order();
            order.setOrderStatus(OrderStatus.PENDING_DELIVERY.name());
            order.setPayMethod(PayMethod.BALANCE_PAY.name());
            order.setPayTime(new Date());
            order.setPayStatus(PayStatus.PAID.name());
            order.setId(bean.getId());
            boolean status = memberService.updateWalletPrice(bean.getMemberId(), bean.getPayPrice());
            if (!status) {
                throw new BusinessException(ResultCode.ERROR, "余额不足，支付失败");
            }
            this.updateByIdSelective(order);
            OrderLog orderLog = new OrderLog(bean.getOrderCode(), "订单[" + bean.getOrderCode() + "]已支付", operator);
            this.sendMqMsg(order.getOrderCode(), OrderStatus.PENDING_DELIVERY.name());
            return orderLogService.insert(orderLog);

        }
        throw new BusinessException(ResultCode.DATA_CHECK_ERROR, "订单状态不正确");

    }

    @Override
    public Boolean sendMqMsg(String orderCode, String orderStatus) {

        OrderState orderState = new OrderState(orderCode, orderStatus);
        // 1. 创建消息ID，确认机制发送消息时，需要给每个消息设置一个全局唯一 id，以区分不同消息，避免 ack 冲突
        CorrelationData correlationData = new CorrelationData(SnowFlake.getIdString());
        Message message = MessageBuilder
                .withBody(JSON.toJSONString(orderState).getBytes(StandardCharsets.UTF_8))
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        // 3. 发送消息到 RabbitMQ 服务器，需要指定交换机、路由键、消息载体以及消息ID
        rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE, RabbitConfig.order_status_queue_key, message, correlationData);
        return true;
    }
}
