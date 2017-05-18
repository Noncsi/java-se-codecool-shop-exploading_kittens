package com.codecool.shop.dao;


import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by noncsi on 2017.05.11..
 */
class OrderDaoTest {

    static Stream<OrderDao> daoProvider() {
        return Stream.of(OrderDaoMem.getInstance());
    }

    @ParameterizedTest
    @MethodSource(names = "daoProvider")
    public void testOrdersAreExist(OrderDao test){
        //OrderDao test = OrderDaoMem.getInstance();
        List orders = test.getAll();
        assertNotNull(orders);
    }

    @ParameterizedTest
    @MethodSource(names = "daoProvider")
    public void testIsIDBiggerThan0(OrderDao test){
        //OrderDao test = OrderDaoMem.getInstance();
        assertThrows(IllegalArgumentException.class, ()-> {
            test.find(-10);
        });
    }
}