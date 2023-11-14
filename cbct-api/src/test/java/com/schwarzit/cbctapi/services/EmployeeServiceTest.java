package com.schwarzit.cbctapi.services;

import com.schwarzit.cbctapi.enums.Role;
import com.schwarzit.cbctapi.models.CustomerModel;
import com.schwarzit.cbctapi.models.EmployeeModel;
import com.schwarzit.cbctapi.repositories.CustomerRepository;
import com.schwarzit.cbctapi.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTest {

    private static final String TEST_NAME_EMPLOYEE = "TEST123";
    private static final String TEST_NAME_EMPLOYEE2 = "123TEST";
    private static final String TEST_LASTNAME_EMPLOYEE = "TEST321";
    private static final String TEST_EMAIL_EMPLOYEE = "TEST1@MAIL.CBCT";
    private static final String TEST_EMAIL_EMPLOYEE2 = "TEST2@MAIL.CBCT";
    private static final String TEST_PASSWORD_EMPLOYEE = "TESTPASSWORD";
    private static final String TEST_DEPARTMENT_EMPLOYEE = "TESTDEPARTMENT";
    private static final String TEST_DEPARTMENT_EMPLOYEE2 = "TESTDEPARTMENT2";
    private static final Role TEST_ROLE_EMPLOYEE = Role.EMPLOYEE;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    EmployeeModel employeeModelOne;
    EmployeeModel employeeModelTwo;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeModelOne = EmployeeModel.builder()
                .name(TEST_NAME_EMPLOYEE)
                .lastname(TEST_LASTNAME_EMPLOYEE)
                .email(TEST_EMAIL_EMPLOYEE)
                .password(passwordEncoder.encode(TEST_PASSWORD_EMPLOYEE))
                .department(TEST_DEPARTMENT_EMPLOYEE)
                .role(TEST_ROLE_EMPLOYEE)
                .build();
        employeeRepository.save(employeeModelOne);

        employeeModelTwo = EmployeeModel.builder()
                .name(TEST_NAME_EMPLOYEE2)
                .lastname(TEST_LASTNAME_EMPLOYEE)
                .email(TEST_EMAIL_EMPLOYEE2)
                .password(passwordEncoder.encode(TEST_PASSWORD_EMPLOYEE))
                .department(TEST_DEPARTMENT_EMPLOYEE2)
                .role(TEST_ROLE_EMPLOYEE)
                .build();
        employeeRepository.save(employeeModelTwo);
    }

    @Test
    void getAllEmployeesOfDatabase() {
        ResponseEntity<List<EmployeeModel>> actual = employeeService.getAllEmployees();
        assertEquals(2, Objects.requireNonNull(actual.getBody()).size(), "Two Employees found");
    }

    @Test
    void getEmployeeOfDatabaseById() {
        EmployeeModel expected = employeeModelOne;
        EmployeeModel actual = employeeService.getEmployeeById(employeeModelOne.getId()).get();
        assertEquals(expected, actual, "Employee with id found");
    }

    @Test
    void returnEmptyListWhenNotFoundById() {
        Optional<EmployeeModel> expected = Optional.empty();
        Optional<EmployeeModel> actual = employeeService.getEmployeeById(1L);
        assertEquals(expected, actual, "Returned empty list");
    }

    @Test
    void getEmployeeOfDatabaseByEmail() {
        EmployeeModel expected = employeeModelOne;
        EmployeeModel actual = employeeService.getEmployeeByEmail(employeeModelOne.getEmail()).get();
        assertEquals(expected, actual, "Employee with email found");
    }

    @Test
    void returnEmptyListWhenNotFoundByEmail() {
        Optional<EmployeeModel> expected = Optional.empty();
        Optional<EmployeeModel> actual = employeeService.getEmployeeByEmail("TEST@MAIL.CBCT");
        assertEquals(expected, actual, "Returned empty list");
    }

    @Test
    void returnEmptyListWhenNotFoundByName() {
        Optional<EmployeeModel> expected = Optional.empty();
        Optional<EmployeeModel> actual = employeeService.getEmployeeByEmail("TEST@MAIL.CBCT");
        assertEquals(expected, actual, "Returned empty list");
    }

    @Test
    void getEmployeesOfDatabaseByNameOne() {
        ResponseEntity<List<EmployeeModel>> expected = ResponseEntity.ok(Collections.singletonList(employeeModelOne));
        ResponseEntity<List<EmployeeModel>> actual = employeeService.getEmployeeByName(employeeModelOne.getName());
        assertEquals(expected, actual, "Employee with name TEST123 found");
    }

    @Test
    void getEmployeeOfDatabaseByNameTwo() {
        ResponseEntity<List<EmployeeModel>> expected = ResponseEntity.ok(Collections.singletonList(employeeModelTwo));
        ResponseEntity<List<EmployeeModel>> actual = employeeService.getEmployeeByName(employeeModelTwo.getName());
        assertEquals(expected, actual, "Employee with name 123TEST found");
    }

    @Test
    void getEmployeeOfDatabaseByLastname() {
        ResponseEntity<List<EmployeeModel>> expected = ResponseEntity.ok(employeeRepository.findAll());
        ResponseEntity<List<EmployeeModel>> actual = employeeService.getEmployeeByLastname(employeeModelOne.getLastname());
        assertEquals(expected, actual, "Employee with Lastname TEST321 found");
    }

    @Test
    void getEmployeeOfDatabaseByDepartment() {
        ResponseEntity<List<EmployeeModel>> expected = ResponseEntity.ok(Collections.singletonList(employeeModelOne));
        ResponseEntity<List<EmployeeModel>> actual = employeeService.getEmployeeByDepartment(employeeModelOne.getDepartment());
        assertEquals(expected, actual, "Employee with department found");
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }
}