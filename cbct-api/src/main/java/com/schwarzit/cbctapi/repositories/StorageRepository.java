package com.schwarzit.cbctapi.repositories;

import com.schwarzit.cbctapi.models.StorageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<StorageModel, Long> {
}
