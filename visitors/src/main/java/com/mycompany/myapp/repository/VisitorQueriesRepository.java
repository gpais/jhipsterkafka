package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VisitorQueries;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VisitorQueries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitorQueriesRepository extends JpaRepository<VisitorQueries, Long> {

}
