package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Aggregate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Aggregate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AggregateRepository extends JpaRepository<Aggregate, Long> {

}
