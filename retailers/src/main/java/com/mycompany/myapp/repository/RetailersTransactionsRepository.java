package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RetailersTransactions;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RetailersTransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RetailersTransactionsRepository extends JpaRepository<RetailersTransactions, Long> {

}
