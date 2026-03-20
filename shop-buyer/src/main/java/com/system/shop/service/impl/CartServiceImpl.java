package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;

import com.system.shop.bean.cart.Cart;
import com.system.shop.bean.cart.CartItem;
import com.system.shop.bean.cart.CartProductItem;
import com.system.shop.bean.cart.CartSku;
import com.system.shop.bean.cart.CartStoreActivityItem;
import com.system.shop.bean.cart.CartStoreItem;
import com.system.shop.bean.cart.ProductSettlement;
import com.system.shop.bean.cart.ProductSkuItem;
import com.system.shop.bean.cart.Settlement;
import com.system.shop.bean.cart.ShoppSettlement;
import com.system.shop.bean.cart.StoreSettlement;
import com.system.shop.common.ResultCode;
import com.system.shop.entity.*;
import com.system.shop.enumer.CartEnum;
import com.system.shop.enumer.SettlementStep;
import com.system.shop.enumer.SettlementType;
import com.system.shop.exception.BusinessException;
import com.system.shop.service.*;
import com.system.shop.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/10 17:04
 * @Description：
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private StoreService storeService;
    @Autowired
    private MemberAddressService memberAddressService;
    @Autowired
    private List<SettlementService> settlementServices;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;

    @Override
    public Boolean add(Long memberId, CartSku cartSku) {
        Cart cart = RedisUtil.getObject(CartEnum.CART.getPrefix() + memberId,Cart.class);
        if (cart == null) {
            cart = new Cart(memberId, new ArrayList<CartSku>());
        }
        boolean notExist = true;
        for (CartSku bean : cart.getSkus()) {
            if (Objects.equals(bean.getSkuCode(), cartSku.getSkuCode()) && Objects.equals(bean.getStoreCode(), cartSku.getStoreCode())) {
                bean.setNum(bean.getNum() + cartSku.getNum());
                bean.setSelected(true);
                notExist = false;
            }
        }
        if (notExist) {
            cartSku.setSelected(true);
            cart.getSkus().add(cartSku);
        }
        RedisUtil.setObject(CartEnum.CART.getPrefix() + memberId, cart);
        return true;
    }

    @Override
    public Boolean updateNum(Long memberId, CartSku cartSku) {
        Cart cart = RedisUtil.getObject(CartEnum.CART.getPrefix() + memberId, Cart.class);
        if (cart == null) {
            cart = new Cart(memberId, new ArrayList<CartSku>());
        }
        boolean notExist = true;
        for (CartSku bean : cart.getSkus()) {
            if (Objects.equals(bean.getSkuCode(), cartSku.getSkuCode()) && Objects.equals(bean.getStoreCode(), cartSku.getStoreCode())) {
                bean.setNum(cartSku.getNum());
                notExist = false;
            }
        }
        if (notExist) {
            cart.getSkus().add(cartSku);
        }
        RedisUtil.setObject(CartEnum.CART.getPrefix() + memberId, cart);
        return true;
    }

    @Override
    public Boolean updateProduct(Long memberId, ProductSkuItem productSkuItem) {
        return null;
    }

    @Override
    public Boolean delete(Long memberId, List<ProductSkuItem> productSkuItemList) {
        Map<Long, List<ProductSkuItem>> listMap = productSkuItemList.stream().collect(Collectors.groupingBy(ProductSkuItem::getStoreId));
        Map<Long, List<Long>> deleteMap = new HashMap<>();
        listMap.forEach(new BiConsumer<Long, List<ProductSkuItem>>() {
            @Override
            public void accept(Long storeId, List<ProductSkuItem> productSkuItems) {
                deleteMap.put(storeId, productSkuItems.stream().map(ProductSkuItem::getProductSkuId).collect(Collectors.toList()));
            }
        });

        CartItem cartItem = RedisUtil.getObject(CartEnum.CART.getPrefix() + memberId, CartItem.class);
        Iterator<CartStoreItem> storeCartItemsIterator = cartItem.getStoreItems().iterator();
        while (storeCartItemsIterator.hasNext()) {
            CartStoreItem storeCartItem = storeCartItemsIterator.next();
            List<Long> deleteList = deleteMap.get(storeCartItem.getStoreCode());
            if (CollectionUtil.isNotEmpty(deleteList)) {
                storeCartItem.getProductItems().removeIf(bean -> deleteList.contains(bean.getProductSkuId()));
                if (CollectionUtils.isEmpty(storeCartItem.getProductItems())) {
                    storeCartItemsIterator.remove();
                }
            }

        }
        RedisUtil.setObject(CartEnum.CART.getPrefix() + memberId, cartItem);
        return true;
    }

    @Override
    public Boolean checked(Long memberId, CartSku cartSku, CartEnum cartChecked) {
        Cart cart = RedisUtil.getObject(CartEnum.CART.getPrefix() + memberId, Cart.class);
        if (cart == null || CollectionUtils.isEmpty(cart.getSkus())) {
            return false;
        }
        switch (cartChecked) {
            case ALL:
                for (CartSku bean : cart.getSkus()) {
                    bean.setSelected(cartSku.isSelected());
                }
            case STORE:
                for (CartSku bean : cart.getSkus()) {
                    if (Objects.equals(bean.getStoreCode(), cartSku.getStoreCode())) {
                        bean.setSelected(cartSku.isSelected());
                    }
                }
            case PRODUCT:
                for (CartSku bean : cart.getSkus()) {
                    if (Objects.equals(bean.getStoreCode(), cartSku.getStoreCode()) && Objects.equals(bean.getProductCode(), cartSku.getProductCode()) && Objects.equals(bean.getSkuCode(), cartSku.getSkuCode())) {
                        bean.setSelected(cartSku.isSelected());
                    }
                }
        }
        RedisUtil.setObject(CartEnum.CART.getPrefix() + memberId, cart);
        return true;
    }

    @Override
    public Boolean deleteProduct(Long memberId, CartSku cartSku) {
        Cart cart = RedisUtil.getObject(CartEnum.CART.getPrefix() + memberId, Cart.class);
        if (cart == null || CollectionUtils.isEmpty(cart.getSkus())) {
            return false;
        }
        cart.getSkus().removeIf(bean -> bean.getStoreCode().equals(cartSku.getStoreCode()) && bean.getProductCode().equals(cartSku.getProductCode()) && bean.getSkuCode().equals(cartSku.getSkuCode()));
        RedisUtil.setObject(CartEnum.CART.getPrefix() + memberId, cart);
        return true;
    }

    @Autowired
    private ActivityService activityService;

    @Override
    public CartItem all(Long memberId) {
//        Cart cart = RedisUtil.getObject(CartEnum.CART.getPrefix() + memberId);
//        Map<String, List<CartProductItem>> cartMap = new HashMap<>();
//        List<CartSku> skus = cart.getSkus();
//        BigDecimal totalPrice = BigDecimal.ZERO;
//        int totalNum = 0;
//        for (CartSku cartSku : skus) {
//            List<CartProductItem> list = cartMap.get(cartSku.getStoreCode());
//            CartProductItem cartProductItem = init(cartSku);
//            if (CollectionUtil.isNotEmpty(list)) {
//                list.add(cartProductItem);
//            } else {
//                list = new ArrayList<>();
//                list.add(cartProductItem);
//                cartMap.put(cartSku.getStoreCode(), list);
//            }
//            if (cartProductItem.isSelected()) {
//                totalPrice = totalPrice.add(cartProductItem.getSalePrice().multiply(new BigDecimal(cartProductItem.getNum())));
//                totalNum = totalNum + cartProductItem.getNum();
//            }
//        }
//
//        List<CartStoreItem> storeItems = new ArrayList<>();
//        BigDecimal activityPrice = BigDecimal.ZERO;
//        for (Map.Entry<String, List<CartProductItem>> entry : cartMap.entrySet()) {
//            String storeCode = entry.getKey();
//            CartStoreItem cartStoreItem = new CartStoreItem();
//            cartStoreItem.setStoreCode(storeCode);
//            cartStoreItem.setStoreName(storeService.selectById(storeCode).getStoreName());
//            cartStoreItem.setShortageProductItems(entry.getValue().stream().filter(bean -> bean.getQuantity() == 0).collect(Collectors.toList()));
//            cartStoreItem.setProductItems(entry.getValue().stream().filter(bean -> (bean.getQuantity() > 0)).collect(Collectors.toList()));
//            List<Boolean> checkedList = cartStoreItem.getProductItems().stream().map(CartProductItem::isSelected).toList();
//            if (CollectionUtil.isNotEmpty(cartStoreItem.getProductItems())) {
//                List<Activity> activityList = activityService.selectActivityByStoreId(cartStoreItem.getStoreCode());
//                List<CartStoreActivityItem> activityItems = new ArrayList<>();
//                for (Activity activity : activityList) {
//                    if (CollectionUtil.isNotEmpty(cartStoreItem.getProductItems())) {
//                        List<CartProductItem> list = new ArrayList<>();
//                        Iterator<CartProductItem> iterator = cartStoreItem.getProductItems().iterator();
//                        while (iterator.hasNext()) {
//                            CartProductItem cartProductItem = iterator.next();
//                            if (cartProductItem.isChecked() && (activity.getScopeType() == 0 || activity.getProductIds().contains(cartProductItem.getProductId()))) {
//                                list.add(cartProductItem);
//                                iterator.remove();
//                            }
//                        }
//                        if (CollectionUtil.isNotEmpty(list)) {
//                            CartStoreActivityItem storeActivityItem = getCartStoreActivityItem(activity, list);
//                            activityItems.add(storeActivityItem);
//                            activityPrice = storeActivityItem.getDiscountPrice() == null ? activityPrice : activityPrice.add(storeActivityItem.getDiscountPrice());
//                        }
//                    }
//                }
//                cartStoreItem.setChecked(checkedList.stream().allMatch(Boolean::booleanValue));
//                cartStoreItem.setActivityItems(activityItems);
//            }
//            storeItems.add(cartStoreItem);
//        }
        //return new CartItem(memberId, storeItems, totalPrice, totalNum, activityPrice);
        return null;
    }

    private CartStoreActivityItem getCartStoreActivityItem(Activity activity, List<CartProductItem> list) {
        CartStoreActivityItem storeActivityItem = new CartStoreActivityItem();
        storeActivityItem.setActivityName(activity.getName());
        storeActivityItem.setDescription(activity.getDescription());
        storeActivityItem.setActivityType(activity.getType() == 1 ? "满减" : "满折扣");
        storeActivityItem.setProductItems(list);
        storeActivityItem.setIsNeedAddOnShop(0);
        BigDecimal price = BigDecimal.ZERO;
        int num = 0;
        for (CartProductItem cartProductItem : list) {
            price = price.add(cartProductItem.getSalePrice().multiply(new BigDecimal(cartProductItem.getNum())));
            num = num + cartProductItem.getNum();
        }
        if (activity.getFullType() != null && activity.getFullType() == 1) {
            if (num >= activity.getFullAmount() && price.compareTo(activity.getFullMoney()) >= 0) {
                storeActivityItem.setIsNeedAddOnShop(1);
            }
        } else {
            if (activity.getFullMoney() != null && price.compareTo(activity.getFullMoney()) >= 0) {
                storeActivityItem.setIsNeedAddOnShop(1);
            }
            if (activity.getFullAmount() != null && num >= activity.getFullAmount()) {
                storeActivityItem.setIsNeedAddOnShop(1);
            }
        }
        if (storeActivityItem.getIsNeedAddOnShop() == 1 && activity.getType() == 1) {
            storeActivityItem.setDiscountPrice(activity.getDiscountPrice());
        } else if (storeActivityItem.getIsNeedAddOnShop() == 1 && activity.getType() == 2) {
            storeActivityItem.setDiscountPrice(price.subtract(price.multiply(activity.getDiscountPrice().divide(new BigDecimal(100)))));
        }
        return storeActivityItem;
    }

    private CartProductItem init(CartSku cartSku) {
        CartProductItem cartProductItem = new CartProductItem(cartSku);
        ProductSku sku = productSkuService.selectById(cartProductItem.getProductSkuId());
        Product product = productService.selectById(sku.getProductId());
        cartProductItem.setProductCode(product.getProductCode());
        cartProductItem.setProductName(product.getProductName());
        cartProductItem.setProductId(product.getId());
        cartProductItem.setSkuCode(sku.getProductSkuCode());
        cartProductItem.setSalePrice(product.getSalePrice());
        cartProductItem.setSpecInfo(sku.getSpecInfo());
        cartProductItem.setImage(product.getHeadImage());
        cartProductItem.setQuantity(sku.getQuantity());
        cartProductItem.setNum(cartProductItem.getNum() > cartProductItem.getQuantity() ? cartProductItem.getQuantity() : cartProductItem.getNum());
        cartProductItem.setSelected(cartSku.isSelected() && cartProductItem.getNum() > 0);
        return cartProductItem;
    }

    @Override
    public Integer count(Long memberId) {
        Cart cart = RedisUtil.getObject(CartEnum.CART.getPrefix() + memberId, Cart.class);
        return cart != null ? cart.getSkus().size() : 0;
    }

    @Override
    public ShoppSettlement settlement(Long memberId, Settlement settlement) {
        ShoppSettlement shoppSettlement = createShoppSettlement(memberId, settlement);
        Map<SettlementType, SettlementService> map = settlementServices.stream().collect(Collectors.toMap(SettlementService::step, Function.identity()));
        for (SettlementType settlementType : SettlementStep.settlementOrder()) {
            map.get(settlementType).compute(shoppSettlement);
        }
        RedisUtil.setObject(CartEnum.BUY.getPrefix() + memberId, shoppSettlement);
        return shoppSettlement;
    }

    @Autowired
    private OrderHeaderService orderHeaderService;

    @Override
    public OrderHeader createOrder(Long memberId, Settlement settlement) {
        ShoppSettlement bean = RedisUtil.getObject(CartEnum.BUY.getPrefix() + memberId, ShoppSettlement.class);
        if (bean == null) {
            bean = this.settlement(memberId, settlement);
        }
        if (CollectionUtil.isNotEmpty(bean.getNotDeliveryProducts())) {
            String products = bean.getNotDeliveryProducts().stream().map(ProductSettlement::getProductName).collect(Collectors.joining(","));
            throw new BusinessException(ResultCode.ORDER_NOT_SUPPORT_DISTRIBUTION, products);
        }
        if (CollectionUtil.isNotEmpty(bean.getNotStockProducts())) {
            String products = bean.getNotStockProducts().stream().map(ProductSettlement::getProductName).collect(Collectors.joining(","));
            throw new BusinessException(ResultCode.ORDER_NOT_EXIST_VALID, products);
        }
        bean.setCouponIds(settlement.getCouponIds());
        OrderHeader orderHeader = orderHeaderService.createOrderHeader(bean);
        RedisUtil.delete(CartEnum.BUY.getPrefix() + memberId);
        //删除购物车中的商品
        return orderHeader;
    }


    private ShoppSettlement createShoppSettlement(Long memberId, Settlement settlement) {
        Map<Long, List<Settlement.SettlementProduct>> listMap = settlement.getProducts().stream().collect(Collectors.groupingBy(Settlement.SettlementProduct::getStoreId));
        List<StoreSettlement> storeSettlements = new ArrayList<>();
        for (Map.Entry<Long, List<Settlement.SettlementProduct>> entry : listMap.entrySet()) {
            List<ProductSettlement> productSettlements = new ArrayList<>();
            Long storeId = entry.getKey();
            List<Settlement.SettlementProduct> list = entry.getValue();
            list.forEach(settlementProduct -> {
                productSettlements.add(new ProductSettlement(settlementProduct.getProductId(), settlementProduct.getProductSkuId(), settlementProduct.getNum()));
            });
            storeSettlements.add(new StoreSettlement(storeId, productSettlements));
        }
        return new ShoppSettlement(memberId, settlement.getAddressId(), settlement.getCouponIds(), storeSettlements);
    }
}
