package com.schwarzit.cbctapi.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
select t from Token t inner join CustomerModel c on t.customerModel.id = c.id
where c.id = :customerId and (t.expired = false or t.revoked = false)
""")
    List<Token> findAllValidTokensByUser(Long customerId);

    Optional<Token> findByToken(String token);
}
