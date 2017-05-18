package com.codecool.shop.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by davidkulchar on 2017.04.26..
 */
/**
 * Here are your products you want to buy.
 */
public class ShoppingCart extends ShopList {

    public Map generatePaymentDetails() {
        Map payment = new HashMap();
        int quantity = 0;
        float fullPrice = 0;
        for (Product product: getProducts()) {
            quantity++;
            fullPrice += product.getHUFPrice();
        }
        payment.put("quantity", quantity);
        payment.put("catnipPrice", fullPrice);
        return payment;
    }
}
