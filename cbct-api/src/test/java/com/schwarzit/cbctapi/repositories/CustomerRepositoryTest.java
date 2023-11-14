package com.schwarzit.cbctapi.repositories;

import com.schwarzit.cbctapi.enums.Role;
import com.schwarzit.cbctapi.models.CustomerModel;
import com.schwarzit.cbctapi.services.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

    private static final String TEST_NAME = "TEST123";
    private static final String TEST_NAME_TWO = "123TEST";
    private static final String TEST_EMAIL_DE = "TEST@XYZ.DE";
    private static final String TEST_EMAIL_EN = "TEST@XYZ.EN";
    private static final String TEST_PASSWORD = "TESTPASSWORD";
    private static final String TEST_COUNTRY_DE = "DE";
    private static final String TEST_COUNTRY_EN = "EN";
    private static final Role TEST_ROLE = Role.USER;

    CustomerModel customerModelEn;
    CustomerModel customerModelDe;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerModelDe = CustomerModel.builder()
                .name(TEST_NAME)
                .email(TEST_EMAIL_DE)
                .password(TEST_PASSWORD)
                .country(TEST_COUNTRY_DE)
                .role(TEST_ROLE)
                .build();
        customerRepository.save(customerModelDe);

        customerModelEn = CustomerModel.builder()
                .name(TEST_NAME_TWO)
                .email(TEST_EMAIL_EN)
                .password(TEST_PASSWORD)
                .country(TEST_COUNTRY_EN)
                .role(TEST_ROLE)
                .build();
        customerRepository.save(customerModelEn);
    }


    @Test
    void shouldReturnAllCustomers() {
        List<CustomerModel> actual = customerRepository.findAll();
        assertEquals(2, actual.size(), "Two total items found");
    }

    @Test
    void findCustomerOfDatabaseById() {
        CustomerModel expected = customerModelDe;
        CustomerModel actual = customerRepository.findById(customerModelDe.getId()).get();
        assertEquals(expected, actual);
    }

    @Test
    void checkIfCustomerExistsByEmail() {
        boolean actual = customerRepository.existsByEmail(customerModelDe.getEmail());
        assertTrue(actual);
    }

    @Test
    void checkIfCustomerExistsByName() {
        boolean actual = customerRepository.existsByName(customerModelDe.getName());
        assertTrue(actual);
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }
}