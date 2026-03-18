package com.system.shop.controller.order;

import com.github.pagehelper.PageInfo;
import com.system.shop.bean.order.OrderTagInfo;
import com.system.shop.bean.search.OrderSearch;
import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.Order;
import com.system.shop.entity.OrderLog;
import com.system.shop.entity.OrderLogistics;
import com.system.shop.service.OrderItemService;
import com.system.shop.service.OrderLogService;
import com.system.shop.service.OrderLogisticsService;
import com.system.shop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/order")
@RestController
public class OrderController extends BaseController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderLogService orderLogService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderLogisticsService orderLogisticsService;

    @PostMapping("/selectPage")
    public Result<PageInfo<Order>> findPage(@RequestBody OrderSearch orderSearch, HttpServletRequest request) {
        orderSearch.setMemberId(this.getLoginMember(request).getId());
        PageInfo<Order> pageInfo = orderService.selectPage(orderSearch.getPageNum(), orderSearch.getPageSize(), orderSearch.querymap());
        pageInfo.getList().forEach(order -> {
            order.setOrderItems(orderItemService.selectByOrderCode(order.getOrderCode()));
        });
        return Result.success(pageInfo);
    }


    @GetMapping("/selectByOrderCode")
    public Result<Order> select(String orderCode) {
        return Result.success(orderService.selectByOrderCode(orderCode));
    }


    @GetMapping("/selectOrderLogs")
    public Result<List<OrderLog>> selectOrderLogs(String orderCode) {
        return Result.success(orderLogService.selectByOrderCode(orderCode));
    }


    @GetMapping("/selectOrderTagInfo")
    public Result<Map<String, Integer>> selectOrderTagInfo(HttpServletRequest request) {
        List<OrderTagInfo> list = orderService.selectOrderTagInfo(this.getLoginMember(request).getId());
        Map<String, Integer> map = list.stream().collect(Collectors.toMap(OrderTagInfo::getOrderStatus, OrderTagInfo::getOrderNum));
        return Result.success(map);
    }


    @GetMapping("/selectLogistics")
    public Result<List<OrderLogistics>> selectLogistics(String orderCode) {
        return Result.success(orderLogisticsService.selectOrderLogistics(orderCode));
    }


    @GetMapping("/selectLogisticsId")
    public Result<OrderLogistics> selectLogisticsId(Long orderLogisticsId) {
        return Result.success(orderLogisticsService.selectLogisticsId(orderLogisticsId));
    }

    /**
     * 确认收货
     * @param orderCode
     * @param request
     * @return
     */
    @GetMapping("/completeOrder")
    public Result<Boolean> completeOrder(String orderCode, HttpServletRequest request) {
        return Result.success(orderService.updateCompleteOrder(orderCode, this.getOperator(request)));
    }

    /**
     * 取消订单
     * @param orderCode
     * @param request
     * @return
     */
    @GetMapping("/cancelOrder")
    public Result<Boolean> cancelOrder(String orderCode, HttpServletRequest request) {
        return Result.success(orderService.updateCancelOrder(orderCode, this.getOperator(request)));
    }


    /**
     * 支付订单
     * @param orderCode
     * @param request
     * @return
     */
    @GetMapping("/payOrder")
    public Result<Boolean> payOrder(String orderCode, HttpServletRequest request) {
        return Result.success(orderService.updatePayOrder(orderCode, this.getOperator(request)));
    }




}
