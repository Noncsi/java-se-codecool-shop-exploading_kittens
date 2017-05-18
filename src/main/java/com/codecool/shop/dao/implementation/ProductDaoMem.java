package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDaoMem implements ProductDao {

    private List<Product> DATA = new ArrayList<>();
    private static ProductDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoMem() {
    }

    public static ProductDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        product.setId(DATA.size() + 1);
        DATA.add(product);
    }

    @Override
    /**
     * Finds the data by its ID.
     */
    public Product find(int id) {
        return DATA.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        DATA.remove(find(id));
    }

    @Override
    public List<Product> getAll() {
        return DATA;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return DATA.stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return DATA.stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }

    @Override
    public String getAllProductsJSON(){
        Gson gson = new Gson();
        List<Map> productList = getHashListForJSON(DATA);
        return gson.toJson(productList);
    }


    public String getProductsByCategoryJSON(String catName){
        Gson gson = new Gson();
        List<Product> filteredList = filterProductsByCategory(catName);
        List<Map> productList = getHashListForJSON(filteredList);
        return gson.toJson(productList);
    }

    public String getProductsBySupplierJSON(String supName){
        Gson gson = new Gson();
        List<Product> filteredBySupplierList = filterProductsBySupplier(supName);
        List<Map> prodList = getHashListForJSON(filteredBySupplierList);
        return gson.toJson(prodList);
    }

    private List<Product> filterProductsByCategory(String catName){
        List<Product> filteredList = new ArrayList<>();
        for (Product prod: DATA) {
            if (catName.equals(prod.getProductCategory().getName())) {
                filteredList.add(prod);
            }
        }
        return  filteredList;
    }

    private List<Product> filterProductsBySupplier(String supName){
        List<Product> filteredList = new ArrayList<>();
        for (Product prod: DATA) {
            if (supName.equals(prod.getSupplier().getName())) {
                filteredList.add(prod);
            }
        }
        return  filteredList;
    }

    public List<Map> getHashListForJSON(List<Product> dat) {
        List<Map> prodList = new ArrayList<>();

        for (Product prod: dat) {
            Map product = new HashMap();

            product.put("name", prod.getName());
            product.put("description", prod.getDescription());
            product.put("picture", prod.getPic());
            product.put("category", prod.getProductCategory().getName());
            product.put("supplier", prod.getSupplier().getName());
            product.put("priceTag", prod.getPrice());
            product.put("id", prod.getId());
            prodList.add(product);
        }
        return prodList;
    }


}
