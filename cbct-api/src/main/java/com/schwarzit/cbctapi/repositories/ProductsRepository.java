package com.schwarzit.cbctapi.repositories;

import com.schwarzit.cbctapi.models.ProductsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsModel, Long> {
}
