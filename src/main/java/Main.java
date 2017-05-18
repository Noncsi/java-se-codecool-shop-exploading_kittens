import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.*;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);
        logger.warn("You have to open your localhost with the port:8888");

        // populate some data for the memory storage
        populateData();


        // Always start with more specific routes
        get("/hello", (req, res) -> "Hello World");

        // Always add generic routes to the end
        get("/", ProductController::renderHomePage, new ThymeleafTemplateEngine());
        // Equivalent with above
        logger.trace("Homepage has been loaded.");
        get("/index", (Request req, Response res) -> {
            logger.trace("Homepage has been loaded.");
           return new ThymeleafTemplateEngine().render( ProductController.renderHomePage(req, res) );
        });

        get("/categories/:name", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( ProductController.renderProductsByCategory(req, res) );
            //return productDaoMem.getProductsByCategoryJSON(req.params(":name"));
        });

        get("/suppliers/:name", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( ProductController.renderProductsBySupplier(req, res) );
        });

        get("/cartcount", (Request req, Response res) -> {
            OrderDaoMem orderDaoMem = OrderDaoMem.getInstance();
            return orderDaoMem.getAllProductsJSON();
        });

        get("/categories", (Request req, Response res) -> {
            ProductCategoryDao productCategoryDaoMem = ProductCategoryDaoJdbc.getInstance();
            return productCategoryDaoMem.getAllProductCategoryJSON();
        });

        get("/suppliers", (Request req, Response res) -> {
            SupplierDao suppliers = SupplierDaoJdbc.getInstance();
            return suppliers.getAllSupplierJSON();
        });


        get("/get_products", (Request req, Response res) -> {
            ProductDaoMem productDaoMem = ProductDaoMem.getInstance();
            logger.trace("Products have been returned.");
            return productDaoMem.getAllProductsJSON();
        });

        get("/get_cart", (Request req, Response res) -> {
            OrderDaoMem orderDaoMem = OrderDaoMem.getInstance();
            return orderDaoMem.getAllProductsJSON();
        });

        get("/addToCart/:id", (Request req, Response res) -> {
            ProductDao productDataStore = ProductDaoJdbc.getInstance();
            OrderDaoMem.getInstance().add(productDataStore.find(Integer.parseInt(req.params(":id"))), 1);
            logger.trace("Product with the id:" + req.params(":id") + " has been added to cart.");
            return new ThymeleafTemplateEngine().render( ProductController.renderHomePage(req, res) );
        });

        get("/remove_from_cart/:id", (Request req, Response res) -> {
            OrderDaoMem.getInstance().remove((Integer.parseInt(req.params(":id"))));
            logger.trace("Product with the id:" + req.params(":id") + " has been removed from cart.");
            return new ThymeleafTemplateEngine().render( ProductController.renderHomePage(req, res) );
        });

        // Add this line to your project to enable the debug screen
        enableDebugScreen();
    }

    public static void populateData() {

        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
        SupplierDao supplierDataStore = SupplierDaoJdbc.getInstance();

        //setting up new suppliers
        Supplier getSadCat = new Supplier("GetSadCat", "Cat shelter");
        supplierDataStore.add(getSadCat);
        logger.info("New supplier called '" + getSadCat.getName() + "' have been added.");

        getSadCat = supplierDataStore.find(getSadCat.getName());
        Supplier tamil = new Supplier("Tamil Nadu Industrial", "Explosives");
        supplierDataStore.add(tamil);
        logger.info("New supplier called '" + tamil.getName() + "' have been added.");

        tamil = supplierDataStore.find(tamil.getName());
        Supplier sparkIndustries = new Supplier("Spark Industries", "Explosives");
        supplierDataStore.add(sparkIndustries);
        logger.info("New supplier called '" + sparkIndustries.getName() + "' have been added.");

        sparkIndustries = supplierDataStore.find(sparkIndustries.getName());


        //setting up new product categories
        ProductCategory cat = new ProductCategory("Cats", "Animals", "Cute and fluffy furballs");
        ProductCategory explosives = new ProductCategory("Explosives", "War stuff", "asd");
        productCategoryDataStore.add(cat);
        productCategoryDataStore.add(explosives);
        logger.info("New category called '" + cat.getName() + "' have been added.");
        logger.info("New category called '" + explosives.getName() + "' have been added.");
        cat = productCategoryDataStore.find(cat.getName());
        System.out.println(cat.getId());
        explosives = productCategoryDataStore.find(explosives.getName());

        //setting up products and printing it
        Product transCat = new Product("Transcendence cat", 21, "EUR", "wut", cat, getSadCat, "trans-cat.gif");
        productDataStore.add(transCat);
        logger.info("New product called '" + transCat.getName() + "' have been added.");

        Product nuclBomb = new Product("B61 nuclear bomb", 965, "USD", "BOMMM", explosives, sparkIndustries, "product_6.jpg");
        productDataStore.add(nuclBomb);
        logger.info("New product called '" + nuclBomb.getName() + "' have been added.");

        Product fluffy = new Product("Fluffy", 49, "USD", "When you did not read the terms of Apple and conditions and it said u would turn into a cat and you like WTF but it is too late you are a cat now", cat, getSadCat, "product_1.jpg");
        productDataStore.add(fluffy);
        logger.info("New product called '" + fluffy.getName() + "' have been added.");

        Product gunpowder = new Product("Winchester 760 gunpowder", 479, "EUR", "From China", explosives, tamil, "product_2.jpg");
        productDataStore.add(gunpowder);
        logger.info("New product called '" + gunpowder.getName() + "' have been added.");

        Product pawny = new Product("Pawny", 89, "USD", "Meow.", cat, getSadCat, "product_3.jpg");
        productDataStore.add(pawny);
        logger.info("New product called '" + pawny.getName() + "' have been added.");

        Product igla = new Product("Soviet Union 9K38 Igla ", 89, "USD", "You can see this usually in Russian fail videos", explosives, sparkIndustries, "product_4.jpg");
        productDataStore.add(igla);
        logger.info("New product called '" + igla.getName() + "' have been added.");

        Product tnt = new Product("TNT of Tom", 21, "EUR", "", explosives, tamil, "tom.gif");
        productDataStore.add(tnt);
        logger.info("New product called '" + tnt.getName() + "' have been added.");

        Product nicolas = new Product("Nicolas Cate", 89, "USD", "Purrfect for acting", cat, getSadCat, "product_5.jpg");
        productDataStore.add(nicolas);
        logger.info("New product called '" + nicolas.getName() + "' have been added.");

        Product grumpyCat = new Product("Grumpy cat", 63, "USD", "No", cat, getSadCat, "product_7.jpg");
        productDataStore.add(grumpyCat);
        logger.info("New product called '" + grumpyCat.getName() + "' have been added.");

        Product petard = new Product("BOMB Petard bomb", 89, "EUR", "No, it is not cool if you throw this at people on New Year`s Eve.", explosives, tamil, "product_8.jpg");
        productDataStore.add(petard);
        logger.info("New product called '" + explosives.getName() + "' have been added.");
    }





}
