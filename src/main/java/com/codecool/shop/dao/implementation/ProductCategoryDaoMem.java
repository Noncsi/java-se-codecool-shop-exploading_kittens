package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCategoryDaoMem implements ProductCategoryDao {

    private List<ProductCategory> DATA = new ArrayList<>();
    private static ProductCategoryDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoMem() {
    }

    public static ProductCategoryDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoMem();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        category.setId(DATA.size() + 1);
        DATA.add(category);
    }

    @Override
    /**
     * Finds the data by its ID.
     */
    public ProductCategory find(int id) {
        return DATA.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public ProductCategory find(String name) {
        return DATA.stream().filter(t -> t.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        DATA.remove(find(id));
    }

    @Override
    public List<ProductCategory> getAll() {
        return DATA;
    }


    public String getAllProductCategoryJSON(){
        Gson gson = new Gson();
        List<Map> productCategoryList = getHashListForJSON(DATA);
        return gson.toJson(productCategoryList);
    }

    private List<Map> getHashListForJSON(List<ProductCategory> dat) {
        List<Map> prodCategoryList = new ArrayList<>();

        for (ProductCategory prodCat: dat) {
            Map product = new HashMap();
            product.put("name", prodCat.getName());
            prodCategoryList.add(product);
        }
        return prodCategoryList;
    }
}
