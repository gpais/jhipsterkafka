package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VisitorTrip;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VisitorTrip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitorTripRepository extends JpaRepository<VisitorTrip, Long> {

}
