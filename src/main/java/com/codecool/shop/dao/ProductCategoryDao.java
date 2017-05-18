package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;

import java.util.List;

/**
 * Inteface of ProductCategoryDaoJdbc andProductCategoryDaoMem class
 */
public interface ProductCategoryDao {

    void add(ProductCategory category);
    ProductCategory find(int id);
    ProductCategory find(String name);
    void remove(int id);
    String getAllProductCategoryJSON();
    List<ProductCategory> getAll();

}
