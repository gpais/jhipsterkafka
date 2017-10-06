package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Retailers;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Retailers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RetailersRepository extends JpaRepository<Retailers, Long> {

}
