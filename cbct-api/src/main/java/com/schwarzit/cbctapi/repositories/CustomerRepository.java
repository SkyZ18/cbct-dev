package com.schwarzit.cbctapi.repositories;

import com.schwarzit.cbctapi.enums.Role;
import com.schwarzit.cbctapi.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

    boolean existsByName(String name);
    boolean existsByEmail(String email);

    @Query("""
select e from CustomerModel e where e.email = :email
""")
    Optional<CustomerModel> findByEmail(String email);

    @Query("""
select n from CustomerModel n where n.name = :name
""")
    Optional<CustomerModel> findByName(String name);

    @Query("""
select c from CustomerModel c where c.country = :country
""")
    List<CustomerModel> findCustomerByCountry(String country);

}
