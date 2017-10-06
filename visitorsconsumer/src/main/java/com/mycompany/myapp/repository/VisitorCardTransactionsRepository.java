package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VisitorCardTransactions;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VisitorCardTransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitorCardTransactionsRepository extends JpaRepository<VisitorCardTransactions, Long> {
	
	public List<VisitorCardTransactions> findByTfscNumber(String tfscNumber);
	

}
