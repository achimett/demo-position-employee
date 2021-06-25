package com.achimett.demopositionemployee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PositionRepositoryIntegrationTest {

    private Position cashier;
    private Position manager;

    @Autowired
    private PositionRepository repo;

    @BeforeEach
    void setUp() {
        cashier = new Position("Cashier", "Works at the cash desk");
        manager = new Position("Manager", "Manages the business");

        repo.deleteAll();
        repo.save(cashier);
        repo.save(manager);
    }

    @Test
    void findByDescContains_InputStringThe_ReturnsBothEntries() {
        List<Position> expected = Arrays.asList(cashier, manager);
        List<Position> actual = repo.findByDescContains("the");
        actual.forEach(position -> {
            assertTrue(expected.contains(position));
        });
    }
}
