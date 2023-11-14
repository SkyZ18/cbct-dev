package com.schwarzit.cbctapi.services;

import com.schwarzit.cbctapi.enums.Role;
import com.schwarzit.cbctapi.models.CustomerModel;
import com.schwarzit.cbctapi.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

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
    CustomerService customerService;
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
    void getAllCustomersFromDatabase() {
        ResponseEntity<List<CustomerModel>> actual = customerService.getAllCustomers();
        assertEquals(2, Objects.requireNonNull(actual.getBody()).size(), "Two total items found");
    }

    @Test
    void getCustomerFromDatabaseById() {
        CustomerModel expected = customerModelDe;
        CustomerModel actual = customerService.getCustomerById(customerModelDe.getId()).get();
        assertEquals(expected, actual, "DE Customer found");
    }

    @Test
    void returnEmptyListOfCustomersWhenNotFoundById() {
        Optional<CustomerModel> expected = Optional.empty();
        Optional<CustomerModel> actual = customerService.getCustomerById(1L);
        assertEquals(expected, actual, "No list returned");
    }

    @Test
    void getCustomerOfDatabaseByName() {
        CustomerModel expected = customerModelDe;
        CustomerModel actual = customerService.getCustomerByName(customerModelDe.getName()).get();
        assertEquals(expected, actual, "Customer with name TEST123 found");
    }

    @Test
    void returnEmptyListWhenNotFoundByName() {
        Optional<CustomerModel> expected = Optional.empty();
        Optional<CustomerModel> actual = customerService.getCustomerByName("TEST");
        assertEquals(expected, actual, "No list returned");
    }

    @Test
    void getCustomerFromDatabaseByEmailDe() {
        CustomerModel expected = customerModelDe;
        CustomerModel actual = customerService.getCustomerByEmail(TEST_EMAIL_DE).get();
        assertEquals(expected, actual, "DE Customer email found");
    }

    @Test
    void getCustomerFromDatabaseByEmailEn() {
        CustomerModel expected = customerModelEn;
        CustomerModel actual = customerService.getCustomerByEmail(TEST_EMAIL_EN).get();
        assertEquals(expected, actual, "EN Customer email found");
    }

    @Test
    void returnEmptyListOfCustomersWhenNotFoundByEmail() {
        Optional<CustomerModel> expected = Optional.empty();
        Optional<CustomerModel> actual = customerService.getCustomerByEmail("TEST@XYZ.TEST");
        assertEquals(expected, actual, "No list returned");
    }

    @Test
    void getAllCustomersWithCountryDe() {
        ResponseEntity<List<CustomerModel>> expected = ResponseEntity.ok(Collections.singletonList(customerModelDe));
        ResponseEntity<List<CustomerModel>> actual = customerService.getAllCustomersOfOneCountry(TEST_COUNTRY_DE);
        assertEquals(expected, actual, "Returned Customer of country DE");
        assertEquals(1, Objects.requireNonNull(actual.getBody()).size(), "Returned one Customer");
    }

    @Test
    void getAllCustomersWithCountryEn() {
        ResponseEntity<List<CustomerModel>> expected = ResponseEntity.ok(Collections.singletonList(customerModelEn));
        ResponseEntity<List<CustomerModel>> actual = customerService.getAllCustomersOfOneCountry(TEST_COUNTRY_EN);
        assertEquals(expected, actual, "Returned Customer of country EN");
        assertEquals(1, Objects.requireNonNull(actual.getBody()).size(), "Returned one Customer");
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }
}