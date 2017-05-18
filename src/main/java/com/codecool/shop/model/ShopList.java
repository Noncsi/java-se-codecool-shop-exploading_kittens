package com.codecool.shop.model;

import java.util.ArrayList;

/**
 * Created by davidkulchar on 2017.04.26..
 */

public abstract class ShopList{
    private ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts(){
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(int id, int i) {
        int e = 0;
        while (e < i) {
            for (Product prod: products) {
                if (id == prod.id) {
                    products.remove(prod);
                    e++;
                }
            }
        }
    }

    public int getListSize() {
        return products.size();
    }

}
