package com.schwarzit.cbctapi.models;

import com.schwarzit.cbctapi.enums.Role;
import com.schwarzit.cbctapi.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeModelTest {

    private static final String TEST_NAME = "TEST123";
    private static final String TEST_LASTNAME = "TEST321";
    private static final String TEST_EMAIL = "TEST@XYZ.COM";
    private static final String TEST_PASSWORD = "TESTPASSWORD";
    private static final String TEST_DEPARTMENT = "TESTDEPARTMENT";
    private static final Role TEST_ROLE = Role.EMPLOYEE;

    EmployeeModel employeeModel;
    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeModel = EmployeeModel.builder()
                .name(TEST_NAME)
                .lastname(TEST_LASTNAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .department(TEST_DEPARTMENT)
                .role(TEST_ROLE)
                .build();
        employeeRepository.save(employeeModel);
    }

    @Test
    void getAllInfosById() {
        Optional<EmployeeModel> expected = Optional.of(employeeModel);
        Optional<EmployeeModel> actual = employeeRepository.findById(employeeModel.getId());
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }
}