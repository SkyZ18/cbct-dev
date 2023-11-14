package com.schwarzit.cbctapi.controllers;

import com.schwarzit.cbctapi.dtos.RegisterCustomerRequest;
import com.schwarzit.cbctapi.dtos.RegisterCustomerResponse;
import com.schwarzit.cbctapi.models.CustomerModel;
import com.schwarzit.cbctapi.repositories.CustomerRepository;
import com.schwarzit.cbctapi.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerModel>> getAll() {
        return ResponseEntity.ok(service.getAllCustomers());
    }

    @GetMapping("/getById/{id}")
    public Optional<CustomerModel> getById(@PathVariable("id") Long id) {
        return service.getCustomerById(id);
    }

    @GetMapping("/getByName/{name}")
    public Optional<CustomerModel> getByName(@PathVariable("name") String name) {
        return service.getCustomerByName(name);
    }

    @GetMapping("/getByEmail/{email}")
    public Optional<CustomerModel> getByEmail(@PathVariable("email") String email) {
        return service.getCustomerByEmail(email);
    }

    @GetMapping("/getByCountry/{country}")
    public ResponseEntity<List<CustomerModel>> getByCountry(@PathVariable("country") String country) {
        return service.getAllCustomersOfOneCountry(country);
    }

    @PostMapping("/register")
    public RegisterCustomerResponse registerCustomer(@RequestBody RegisterCustomerRequest request) {
        return service.registerNewCustomer(request);
    }
}
