package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ProductDaoJdbc implements ProductDao {

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    
    private static ProductDaoJdbc ourInstance = new ProductDaoJdbc();

    public static ProductDaoJdbc getInstance() {
        return ourInstance;
    }

    private ProductDaoJdbc(){}

    @Override
    public void add(Product product) {
        String query = "INSERT INTO product (" +
                "name, " +
                "description, " +
                "default_price, " +
                "default_currency, " +
                "pic, " +
                "supplier, " +
                "product_category) " +
                "VALUES ('"
                + product.getName() + "', '"
                + product.getDescription() + "', "
                + product.getDefaultPrice() + ", '"
                + product.getDefaultCurrency() + "', '"
                + product.getPic() + "', "
                + product.getSupplier().getId() + ", "
                + product.getProductCategory().getId()
                + ");";
        executeQuery(query);
    }

    @Override
    /**
     * Finds the data by its ID.
     */
    public Product find(int id) {
        String query = "SELECT * FROM product " +
                "WHERE id =" + String.valueOf(id) + ";";

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            if (resultSet.next()){
                SupplierDaoJdbc SupplierDataStore = SupplierDaoJdbc.getInstance();
                Supplier supplier = SupplierDataStore.find(resultSet.getInt("supplier"));

                ProductCategoryDaoJdbc ProdCatDataStore = ProductCategoryDaoJdbc.getInstance();
                ProductCategory productCategory = ProdCatDataStore.find(resultSet.getInt("product_category"));

                Product result = new Product(resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("default_currency"),
                        resultSet.getString("description"),
                        productCategory,
                        supplier,
                        resultSet.getString("pic"));
                result.setId(resultSet.getInt("id"));
                return result;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE * FROM product " +
                "WHERE id ='" + String.valueOf(id) + "';";
        executeQuery(query);
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM product;";

        List<Product> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                SupplierDaoJdbc SupplierDataStore = SupplierDaoJdbc.getInstance();
                Supplier supplier = SupplierDataStore.find(resultSet.getInt("supplier"));

                ProductCategoryDaoJdbc ProdCatDataStore = ProductCategoryDaoJdbc.getInstance();
                ProductCategory productCategory = ProdCatDataStore.find(resultSet.getInt("product_category"));

                Product product = new Product(resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("default_currency"),
                        resultSet.getString("description"),
                        productCategory,
                        supplier,
                        resultSet.getString("pic"));
                product.setId(resultSet.getInt("id"));
                resultList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        String query = "SELECT * FROM product WHERE supplier = " + String.valueOf(supplier.getId()) + ";";

        List<Product> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                ProductCategoryDaoJdbc ProdCatDataStore = ProductCategoryDaoJdbc.getInstance();
                ProductCategory productCategory = ProdCatDataStore.find(resultSet.getInt("product_category"));

                Product product = new Product(resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("default_currency"),
                        resultSet.getString("description"),
                        productCategory,
                        supplier,
                        resultSet.getString("pic"));
                product.setId(resultSet.getInt("id"));
                resultList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        String query = "SELECT * FROM product WHERE product_category = '" + String.valueOf(productCategory.getId()) + "';";

        List<Product> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                SupplierDaoJdbc SupplierDataStore = SupplierDaoJdbc.getInstance();
                Supplier supplier = SupplierDataStore.find(resultSet.getInt("supplier"));

                Product product = new Product(resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("default_currency"),
                        resultSet.getString("description"),
                        productCategory,
                        supplier,
                        resultSet.getString("pic"));
                product.setId(resultSet.getInt("id"));
                resultList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public String getAllProductsJSON(){
        Gson gson = new Gson();
        List<Map> productList = ProductDaoMem.getInstance().getHashListForJSON(getAll());
        return gson.toJson(productList);
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
