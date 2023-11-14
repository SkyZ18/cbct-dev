package com.schwarzit.cbctapi.services;

import com.schwarzit.cbctapi.dtos.RegisterCustomerRequest;
import com.schwarzit.cbctapi.dtos.RegisterCustomerResponse;
import com.schwarzit.cbctapi.enums.Role;
import com.schwarzit.cbctapi.models.CustomerModel;
import com.schwarzit.cbctapi.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {
    private static final String TEST_NAME_CUSTOMER = "TEST123";
    private static final String TEST_NAME_CUSTOMER2 = "123TEST";
    private static final String TEST_NAME_CREATE_CUSTOMER = "CREATETEST";
    private static final String TEST_EMAIL_DE_CUSTOMER = "TEST@XYZ.DE";
    private static final String TEST_EMAIL_EN_CUSTOMER = "TEST@XYZ.EN";
    private static final String TEST_PASSWORD_CUSTOMER = "TESTPASSWORD";
    private static final String TEST_COUNTRY_DE_CUSTOMER = "DE";
    private static final String TEST_COUNTRY_EN_CUSTOMER = "EN";
    private static final Role TEST_ROLE_CUSTOMER = Role.USER;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    CustomerModel customerModelEn;
    CustomerModel customerModelDe;
    RegisterCustomerRequest register;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerModelDe = CustomerModel.builder()
                .name(TEST_NAME_CUSTOMER)
                .email(TEST_EMAIL_DE_CUSTOMER)
                .password(passwordEncoder.encode(TEST_PASSWORD_CUSTOMER))
                .country(TEST_COUNTRY_DE_CUSTOMER)
                .role(TEST_ROLE_CUSTOMER)
                .build();
        customerRepository.save(customerModelDe);

        customerModelEn = CustomerModel.builder()
                .name(TEST_NAME_CUSTOMER2)
                .email(TEST_EMAIL_EN_CUSTOMER)
                .password(passwordEncoder.encode(TEST_PASSWORD_CUSTOMER))
                .country(TEST_COUNTRY_EN_CUSTOMER)
                .role(TEST_ROLE_CUSTOMER)
                .build();
        customerRepository.save(customerModelEn);

        register = RegisterCustomerRequest.builder()
                .name(TEST_NAME_CREATE_CUSTOMER)
                .email(TEST_EMAIL_DE_CUSTOMER)
                .password(passwordEncoder.encode(TEST_PASSWORD_CUSTOMER))
                .country(TEST_COUNTRY_DE_CUSTOMER)
                .build();
    }

    @Test
    void getAllCustomersFromDatabase() {
        List<CustomerModel> actual = customerService.getAllCustomers();
        assertEquals(2, Objects.requireNonNull(actual).size(), "Two total items found");
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
        CustomerModel actual = customerService.getCustomerByEmail(TEST_EMAIL_DE_CUSTOMER).get();
        assertEquals(expected, actual, "DE Customer email found");
    }

    @Test
    void getCustomerFromDatabaseByEmailEn() {
        CustomerModel expected = customerModelEn;
        CustomerModel actual = customerService.getCustomerByEmail(TEST_EMAIL_EN_CUSTOMER).get();
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
        ResponseEntity<List<CustomerModel>> actual = customerService.getAllCustomersOfOneCountry(TEST_COUNTRY_DE_CUSTOMER);
        assertEquals(expected, actual, "Returned Customer of country DE");
        assertEquals(1, Objects.requireNonNull(actual.getBody()).size(), "Returned one Customer");
    }

    @Test
    void getAllCustomersWithCountryEn() {
        ResponseEntity<List<CustomerModel>> expected = ResponseEntity.ok(Collections.singletonList(customerModelEn));
        ResponseEntity<List<CustomerModel>> actual = customerService.getAllCustomersOfOneCountry(TEST_COUNTRY_EN_CUSTOMER);
        assertEquals(expected, actual, "Returned Customer of country EN");
        assertEquals(1, Objects.requireNonNull(actual.getBody()).size(), "Returned one Customer");
    }

    @Test
    void shouldCreateNewCustomer() {
        RegisterCustomerResponse response = customerService.registerNewCustomer(register);
        CustomerModel actualBody = customerRepository.findByName(response.getName()).get();
        CustomerModel expectedBody = CustomerModel.builder()
                .id(actualBody.getId())
                .name(TEST_NAME_CREATE_CUSTOMER)
                .email(TEST_EMAIL_DE_CUSTOMER)
                .password(actualBody.getPassword())
                .country(TEST_COUNTRY_DE_CUSTOMER)
                .role(Role.USER)
                .build();
        assertEquals(expectedBody, actualBody, "Customer was created and found in Database");
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }
}