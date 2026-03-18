package com.system.shop.controller.order;

import com.system.shop.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/10 17:02
 * @Description：
 */
@RequestMapping("/cart")
@RestController
public class CartController extends BaseController {

//    @Autowired
//    private CartService cartService;
//
//    /**
//     * 向购物车中添加一个产品
//     */
//    @PostMapping("/add")
//    public Result<Boolean> add(@RequestBody CartSku cartSku, HttpServletRequest request) {
//        return Result.success(cartService.add(this.getLoginMember(request).getId(), cartSku));
//    }
//
//    /**
//     * 数量
//     *
//     * @param
//     * @return
//     */
//    @GetMapping("/count")
//    public Result<Integer> count(HttpServletRequest request) {
//        return Result.success(cartService.count(this.getLoginMember(request).getId()));
//    }
//
//
//    /**
//     * 修改购物车书数量
//     */
//
//    @PostMapping("/updateNum")
//    public Result<Boolean> updateNum(@RequestBody CartSku cartSku, HttpServletRequest request) {
//        return Result.success(cartService.updateNum(this.getLoginMember(request).getId(), cartSku));
//    }
//
//    @PostMapping("/updateProduct")
//    public Result<Boolean> updateProduct(@RequestBody ProductSkuItem productSkuItem, HttpServletRequest request) {
//        return Result.success(cartService.updateProduct(this.getLoginMember(request).getId(), productSkuItem));
//    }
//
//    /**
//     * 删除购物车中的产品
//     */
//    @PostMapping(value = "/product/remove")
//    public Result<Boolean> deleteProduct(@RequestBody CartSku cartSku, HttpServletRequest request) {
//        return Result.success(cartService.deleteProduct(this.getLoginMember(request).getId(), cartSku));
//    }
//
//    /**
//     * 删除购物车中的产品
//     */
//    @PostMapping(value = "/remove")
//    public Result<Boolean> deleteAll(@RequestBody List<ProductSkuItem> productSkuItems, HttpServletRequest request) {
//        return Result.success(cartService.delete(this.getLoginMember(request).getId(), productSkuItems));
//    }
//
//
//    @PostMapping(value = "/product/checked")
//    public Result<Boolean> updateProductChecked(@RequestBody CartSku cartSku, HttpServletRequest request) {
//        return Result.success(cartService.checked(this.getLoginMember(request).getId(), cartSku, CartEnum.PRODUCT));
//    }
//
//    @PostMapping(value = "/store/checked")
//    public Result<Boolean> updateStoreChecked(@RequestBody CartSku cartSku, HttpServletRequest request) {
//        return Result.success(cartService.checked(this.getLoginMember(request).getId(), cartSku, CartEnum.STORE));
//    }
//
//
//    @PostMapping(value = "/all/checked")
//    public Result<Boolean> updateAllChecked(@RequestBody CartSku cartSku, HttpServletRequest request) {
//        return Result.success(cartService.checked(this.getLoginMember(request).getId(), cartSku, CartEnum.ALL));
//    }
//
//
//    /**
//     * 获取购物车页面购物车详情
//     *
//     * @return
//     */
//    @GetMapping("/all")
//    public Result<CartItem> cartAll(HttpServletRequest request) {
//        return Result.success(cartService.all(this.getLoginMember(request).getId()));
//    }
//
//
//    /**
//     * 立即购买/购物车结算
//     *
//     * @param
//     * @return
//     */
//
//    @PostMapping(value = "/settlement")
//    public Result<ShoppSettlement> settlement(@RequestBody Settlement settlement, HttpServletRequest request) {
//        return Result.success(cartService.settlement(this.getLoginMember(request).getId(), settlement));
//    }
//
//
//    /**
//     * 创建订单
//     *
//     * @param
//     * @return
//     */
//    @PostMapping(value = "/createOrder")
//    public Result<OrderHeader> createOrder(@RequestBody Settlement settlement, HttpServletRequest request) {
//        return Result.success(cartService.createOrder(this.getLoginMember(request).getId(), settlement));
//    }


}
