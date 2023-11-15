package com.schwarzit.cbctapi.services;

import com.schwarzit.cbctapi.enums.Role;
import com.schwarzit.cbctapi.models.CustomerModel;
import com.schwarzit.cbctapi.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<CustomerModel> getCustomerById(Long id) {
        if(customerRepository.existsById(id)) return customerRepository.findById(id);
        else return Optional.empty();
    }

    public Optional<CustomerModel> getCustomerByName(String name) {
        if(customerRepository.existsByName(name)) return customerRepository.findByName(name);
        else return Optional.empty();
    }

    public Optional<CustomerModel> getCustomerByEmail(String email) {
        if(customerRepository.existsByEmail(email)) return customerRepository.findByEmail(email);
        else return Optional.empty();
    }

    public ResponseEntity<List<CustomerModel>> getAllCustomersOfOneCountry(String country) {
        return ResponseEntity.ok(customerRepository.findCustomerByCountry(country));
    }
}
