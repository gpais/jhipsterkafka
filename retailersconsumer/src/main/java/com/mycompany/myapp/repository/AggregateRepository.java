package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Aggregate;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Aggregate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AggregateRepository extends JpaRepository<Aggregate, Long> {
	
	public List<Aggregate> findByAggregateId(String aggregate);
	
}
