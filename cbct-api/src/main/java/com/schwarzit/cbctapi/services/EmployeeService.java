package com.schwarzit.cbctapi.services;

import com.schwarzit.cbctapi.models.EmployeeModel;
import com.schwarzit.cbctapi.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public ResponseEntity<List<EmployeeModel>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    public Optional<EmployeeModel> getEmployeeById(Long id) {
        if (employeeRepository.existsById(id)) return employeeRepository.findById(id);
        else return Optional.empty();
    }

    public ResponseEntity<List<EmployeeModel>> getEmployeeByName(String name) {
        return ResponseEntity.ok(employeeRepository.findByName(name));
    }

    public ResponseEntity<List<EmployeeModel>> getEmployeeByLastname(String lastname) {
        return ResponseEntity.ok(employeeRepository.findByLastname(lastname));
    }

    public Optional<EmployeeModel> getEmployeeByEmail(String email) {
        if (employeeRepository.existsByEmail(email)) return employeeRepository.findByEmail(email);
        else return Optional.empty();
    }

    public ResponseEntity<List<EmployeeModel>> getEmployeeByDepartment(String department) {
        return ResponseEntity.ok(employeeRepository.findByDepartment(department));
    }

}
