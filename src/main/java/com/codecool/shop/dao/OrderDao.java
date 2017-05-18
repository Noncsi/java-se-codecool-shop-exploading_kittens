package com.codecool.shop.dao;

import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.List;
import java.util.Map;

/**
 * Created by davidkulchar on 2017.04.26..
 */
/**
 * Inteface of OrderDaoMem class
 */
public interface OrderDao {
    void add(Product product, int quantity);
    LineItem find(int id);
    void remove(int id);
    int getListSize();
    String getAllProductsJSON();

    List<LineItem> getAll();
//    Map getPaymentDetails();
}
