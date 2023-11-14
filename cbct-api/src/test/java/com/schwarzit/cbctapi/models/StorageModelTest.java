package com.schwarzit.cbctapi.models;

import com.schwarzit.cbctapi.repositories.StorageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StorageModelTest {

    private static final int TEST_QUANTITY = 100;
    private static final String TEST_DESCRIPTION = "THIS IS A TEST";

    StorageModel storageModel;
    @Autowired
    StorageRepository storageRepository;

    @BeforeEach
    void setUp() {
        storageModel = StorageModel.builder()
                .quantity(TEST_QUANTITY)
                .Description(TEST_DESCRIPTION)
                .build();
        storageRepository.save(storageModel);
    }

    @Test
    void getAllInfosById() {
        Optional<StorageModel> expected = Optional.of(storageModel);
        Optional<StorageModel> actual = storageRepository.findById(storageModel.getId());
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        storageRepository.deleteAll();
    }
}