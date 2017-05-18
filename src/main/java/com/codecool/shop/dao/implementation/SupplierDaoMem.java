package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierDaoMem implements SupplierDao {

    private List<Supplier> DATA = new ArrayList<>();
    private static SupplierDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoMem() {
    }

    public static SupplierDaoMem getInstance() {
        if (instance == null) {
            instance = new SupplierDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        supplier.setId(DATA.size() + 1);
        DATA.add(supplier);
    }

    @Override
    /**
     * Finds the data by its ID.
     */
    public Supplier find(int id) {
        return DATA.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public Supplier find(String name) {
        return DATA.stream().filter(t -> t.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        DATA.remove(find(id));
    }

    @Override
    public List<Supplier> getAll() {
        return DATA;
    }


    public String getAllSupplierJSON(){
        Gson gson = new Gson();
        List<Map> supplierList = getHashListForJSON(DATA);
        return gson.toJson(supplierList);
    }

    private List<Map> getHashListForJSON(List<Supplier> dat) {
        List<Map> supplierList = new ArrayList<>();

        for (Supplier supp: dat) {
            Map supplier = new HashMap();
            supplier.put("name", supp.getName());
            supplierList.add(supplier);
        }
        return supplierList;
    }
}
