package com.achimett.demopositionemployee;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// No need for @RepositoryRestResource because default export values are good enough
public interface PositionRepository extends PagingAndSortingRepository<Position, Long> {

    List<Position> findByName(String name);

    List<Position> findByDescContains(@Param("words") String words);

}
