package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Inteface of ProductDaoJdbc and ProductDaoMem class
 */
public interface ProductDao {

    void add(Product product);
    Product find(int id);
    void remove(int id);

    List<Product> getAll();
    List<Product> getBy(Supplier supplier);
    List<Product> getBy(ProductCategory productCategory);
    String getAllProductsJSON();
//    String getProductsByCategoryJSON(String catName);
//    String getProductsBySupplierJSON(String supName);

}
