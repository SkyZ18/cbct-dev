package com.schwarzit.cbctapi.models;

import com.schwarzit.cbctapi.enums.Role;
import com.schwarzit.cbctapi.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerModelTest {

    private static final String TEST_NAME = "TEST123";
    private static final String TEST_EMAIL = "TEST@XYZ.COM";
    private static final String TEST_PASSWORD = "TESTPASSWORD";
    private static final String TEST_COUNTRY = "DE";
    private static final Role TEST_ROLE = Role.USER;

    CustomerModel customerModel;
    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerModel = CustomerModel.builder()
                .name(TEST_NAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .country(TEST_COUNTRY)
                .role(TEST_ROLE)
                .build();
        customerRepository.save(customerModel);
    }

    @Test
    void getAllInfosById() {
        Optional<CustomerModel> expected = Optional.of(customerModel);
        Optional<CustomerModel> actual = customerRepository.findById(customerModel.getId());
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }
}