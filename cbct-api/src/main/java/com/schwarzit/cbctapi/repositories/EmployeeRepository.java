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
select n from EmployeeModel n where n.name = :name
""")
    List<EmployeeModel> findByName(String name);

    @Query("""
select l from EmployeeModel l where l.lastname = :lastname
""")
    List<EmployeeModel> findByLastname(String lastname);

    @Query("""
select e from EmployeeModel e where e.email = :email
""")
    Optional<EmployeeModel> findByEmail(String email);

    @Query("""
select d from EmployeeModel d where d.department = :department
""")
    List<EmployeeModel> findByDepartment(String department);
}
