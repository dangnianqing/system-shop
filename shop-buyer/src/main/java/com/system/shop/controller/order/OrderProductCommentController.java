package com.system.shop.controller.order;


import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.OrderProductComment;
import com.system.shop.service.OrderProductCommentService;
import com.system.shop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderProductComment")
public class OrderProductCommentController extends BaseController {

    @Autowired
    private OrderProductCommentService orderProductCommentService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody OrderProductComment orderProductComment, HttpServletRequest request) {
        return Result.success(orderProductCommentService.save(orderProductComment, this.getLoginMember(request)));
    }


    @GetMapping("/selectComment")
    public Result<OrderProductComment> selectComment(String orderItemCode) {
        return Result.success(orderProductCommentService.selectByOrderItemCode(orderItemCode));
    }
}
