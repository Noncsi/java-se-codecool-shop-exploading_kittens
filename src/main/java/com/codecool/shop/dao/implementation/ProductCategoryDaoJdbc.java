package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductCategoryDaoJdbc implements ProductCategoryDao {
    private static final String DATABASE = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    private static ProductCategoryDaoJdbc ourInstance = new ProductCategoryDaoJdbc();

    public static ProductCategoryDaoJdbc getInstance() {
        return ourInstance;
    }

    private ProductCategoryDaoJdbc(){}

    @Override
    public void add(ProductCategory productCat) {
        String query = "INSERT INTO product_category (" +
                "name, " +
                "description, " +
                "department) " +
                "VALUES ('"
                + productCat.getName() + "', '"
                + productCat.getDescription() + "', '"
                + productCat.getDepartment()
                + "');";
        executeQuery(query);
    }

    @Override
    /**
     * Finds the data by its ID.
     */
    public ProductCategory find(int id) {
        String query = "SELECT * FROM product_category " +
                "WHERE id ='" + String.valueOf(id) + "';";

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            if (resultSet.next()){
                ProductCategory productCat = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
                        productCat.setId(resultSet.getInt("id"));
                return productCat;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProductCategory find(String name) {
        String query = "SELECT * FROM product_category " +
                "WHERE name ='" + name + "';";

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            if (resultSet.next()){
                ProductCategory productCat = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
                productCat.setId(resultSet.getInt("id"));
                return productCat;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void remove(int id){
        String query = "DELETE * FROM product_category " +
                "WHERE id ='" + String.valueOf(id) + "';";
        executeQuery(query);
    }

    @Override
    public List<ProductCategory> getAll() {
        String query = "SELECT * FROM product_category;";

        List<ProductCategory> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                ProductCategory productCat = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
                productCat.setId(resultSet.getInt("id"));
                resultList.add(productCat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public String getAllProductCategoryJSON(){
        Gson gson = new Gson();
        List<Map> productCategoryList = getHashListForJSON(getAll());
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

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    private void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
        ){
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
