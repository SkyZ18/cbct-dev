package com.schwarzit.cbctapi.models;

import com.schwarzit.cbctapi.enums.Role;
import com.schwarzit.cbctapi.enums.Type;
import com.schwarzit.cbctapi.repositories.EmployeeRepository;
import com.schwarzit.cbctapi.repositories.ProductsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductsModelTest {

    private static final String TEST_NAME = "TEST123";
    private static final double TEST_PRICE = 1.99;
    private static final int TEST_QUANTITY = 100;
    private static final Type TEST_TYPE = Type.ENGINE;

    ProductsModel productsModel;
    @Autowired
    ProductsRepository productsRepository;

    @BeforeEach
    void setUp() {
        productsModel = ProductsModel.builder()
                .name(TEST_NAME)
                .price(TEST_PRICE)
                .quantity(TEST_QUANTITY)
                .type(TEST_TYPE)
                .build();
        productsRepository.save(productsModel);
    }

    @Test
    void getAllInfosById() {
        Optional<ProductsModel> expected = Optional.of(productsModel);
        Optional<ProductsModel> actual = productsRepository.findById(productsModel.getId());
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        productsRepository.deleteAll();
    }
}