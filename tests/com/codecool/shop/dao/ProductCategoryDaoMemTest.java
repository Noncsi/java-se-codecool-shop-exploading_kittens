package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by noncsi on 2017.05.11..
 */
class ProductCategoryDaoMemTest {

    static Stream<ProductCategoryDao> daoProvider() {
        return Stream.of(ProductCategoryDaoMem.getInstance());
    }

    @ParameterizedTest
    @MethodSource(names = "daoProvider")
    public void testProductsAreExist(ProductCategoryDao test) {
        //ProductCategoryDao test = ProductCategoryDaoMem.getInstance();
        List categories = test.getAll();
        assertNotNull(categories);
    }

    @ParameterizedTest
    @MethodSource(names = "daoProvider")
    public void testIsIDBiggerThan0(ProductCategoryDao test) {
        //ProductCategoryDaoMem test = ProductCategoryDaoMem.getInstance();
        assertThrows(IllegalArgumentException.class,()-> {
            test.find(-10);
        });
    }

    @Test
    public void testIsGetAllWorking(){
        ProductCategory category1 = new ProductCategory("category_1", "department", "This is a category");
        ProductCategory category2 = new ProductCategory("category_2", "department", "This is a category");
        ProductCategoryDao test = ProductCategoryDaoMem.getInstance();
        test.add(category1);
        test.add(category2);
        assertEquals(2,test.getAll().size());
    }
}