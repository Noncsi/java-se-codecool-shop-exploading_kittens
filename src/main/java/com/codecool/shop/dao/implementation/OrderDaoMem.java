package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoMem implements OrderDao {

    private ArrayList<LineItem> DATA = new ArrayList<>();
    private static OrderDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private OrderDaoMem() {
    }

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }


    @Override
    public void add(Product product, int quantity) {
        boolean match = false;
        for(LineItem item : DATA){
            if(item.getProduct() == product){
                item.increaseQuantity(quantity);
                match = true;
            }
        }
        if(!match) {
            LineItem newItem = new LineItem(product, quantity);
            DATA.add(newItem);
        }
    }

    @Override
    public LineItem find(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be bigger or equal with 0.");
        }
        return DATA.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        DATA.remove(find(id));
    }

    @Override
    public List<LineItem> getAll() {
        return DATA;
    }

    @Override
    public int getListSize() {
        return getAll().size();
    }

    @Override
    public String getAllProductsJSON() {
        Gson gson = new Gson();
        List<Map> productList = getProductHashList();
        Map orderMap = getOrderMap(productList);
        return gson.toJson(orderMap);
    }

    private List<Map> getProductHashList(){
        List<Map> productList = new ArrayList<>();
        for (LineItem ln : DATA) {
            Map product = new HashMap();
            product.put("name", ln.getProduct().getName());
            product.put("priceTag", ln.getProduct().getPrice());
            product.put("id", ln.getProduct().getId());
            product.put("quantity", ln.getQuantity());
            productList.add(product);
        }
        return productList;
    }

    private Map getPaymentDetails() {
        Map payment = new HashMap();
        int quantity = 0;
        float fullPrice = 0;
        for (LineItem item : getAll()) {
            quantity += item.getQuantity();
            fullPrice += item.getProduct().getHUFPrice()*item.getQuantity();
        }
        payment.put("quantity", quantity);
        payment.put("fullPrice", fullPrice);
        return payment;
    }

    private Map getOrderMap(List productList){
        Map payment = getPaymentDetails();
        Map orderMap = new HashMap();
        orderMap.put("quantity", payment.get("quantity"));
        orderMap.put("fullPrice", payment.get("fullPrice"));
        orderMap.put("items", productList);
        return orderMap;
    }
}


