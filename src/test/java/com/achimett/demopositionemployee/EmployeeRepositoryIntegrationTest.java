package com.achimett.demopositionemployee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryIntegrationTest {
    
    private Employee sarah;
    private Employee john;

    @Autowired
    private EmployeeRepository repo;

    @BeforeEach
    void setUp() {
        sarah = new Employee("Sarah", "Baker", Date.valueOf("1990-10-10"), Gender.FEMALE, null);
        john = new Employee("John", "Gulliver", Date.valueOf("1995-09-03"), Gender.MALE, null);

        repo.deleteAll();
        repo.save(sarah);
        repo.save(john);
    }

    @Test
    void findByBirthDateAfter_InputDateNineteenNinetyTwo_ReturnsJohn() {
        List<Employee> expected = Collections.singletonList(john);
        List<Employee> actual = repo.findByBirthDateAfter(Date.valueOf("1992-01-01"));
        actual.forEach(employee -> {
            assertTrue(expected.contains(employee));
        });
    }
}
