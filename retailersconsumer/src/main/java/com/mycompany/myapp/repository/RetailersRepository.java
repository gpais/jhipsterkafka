package com.mycompany.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.Retailers;


/**
 * Spring Data JPA repository for the Retailers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RetailersRepository extends JpaRepository<Retailers, Long> {
      public Retailers findByRetailerNo(String retailerNo);
}
