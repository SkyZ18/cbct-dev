package com.schwarzit.cbctapi.services;

import com.schwarzit.cbctapi.dtos.RegisterCustomerRequest;
import com.schwarzit.cbctapi.dtos.RegisterCustomerResponse;
import com.schwarzit.cbctapi.enums.Role;
import com.schwarzit.cbctapi.models.CustomerModel;
import com.schwarzit.cbctapi.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    public RegisterCustomerResponse registerNewCustomer(RegisterCustomerRequest registerCustomerRequest) throws Exception {
        if (!registerCustomerRequest.getName().isEmpty() && !registerCustomerRequest.getEmail().isEmpty() && !registerCustomerRequest.getPassword().isEmpty() && !registerCustomerRequest.getCountry().isEmpty()) {
            CustomerModel customer = CustomerModel.builder()
                    .name(registerCustomerRequest.getName())
                    .email(registerCustomerRequest.getEmail())
                    .password(passwordEncoder.encode(registerCustomerRequest.getPassword()))
                    .country(registerCustomerRequest.getCountry())
                    .role(Role.USER)
                    .build();
            customerRepository.save(customer);

            return RegisterCustomerResponse.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .email(customer.getEmail())
                    .build();

        } else throw new Exception();


    }

}
