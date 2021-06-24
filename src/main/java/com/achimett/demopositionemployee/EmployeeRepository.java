package com.achimett.demopositionemployee;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

// No need for @RepositoryRestResource because default export values are good enough
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

    List<Employee> findBySurname(String surname);

    List<Employee> findByName(String name);

    List<Employee> findByBirthDateAfter(@Param("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);

    List<Employee> findByGender(Gender gender);

    List<Employee> findByPosition(Position position);
}
