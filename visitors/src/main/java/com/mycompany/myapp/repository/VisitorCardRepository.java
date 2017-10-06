package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VisitorCard;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VisitorCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitorCardRepository extends JpaRepository<VisitorCard, Long> {

}
