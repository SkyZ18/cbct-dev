package com.schwarzit.cbctapi.repositories;

import com.schwarzit.cbctapi.models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {

    boolean existsByEmail(String email);

    @Query("""
select t from EmployeeModel t where t.name = :name
""")
    List<EmployeeModel> findByName(String name);

    @Query("""
select t from EmployeeModel t where t.lastname = :lastname
""")
    List<EmployeeModel> findByLastname(String lastname);

    @Query("""
select t from EmployeeModel t where t.email = :email
""")
    Optional<EmployeeModel> findByEmail(String email);

    @Query("""
select t from EmployeeModel t where t.department = :department
""")
    List<EmployeeModel> findByDepartment(String department);
}
