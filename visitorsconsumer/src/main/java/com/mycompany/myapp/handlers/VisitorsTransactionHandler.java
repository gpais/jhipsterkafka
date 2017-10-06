package com.mycompany.myapp.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.VisitorCardTransactions;
import com.mycompany.myapp.repository.VisitorCardTransactionsRepository;

@Service("VisitorsTransactionHandler")
public class VisitorsTransactionHandler extends AbstractTransactionProcessHandler  {

	public VisitorsTransactionHandler() {
		super(null);
	}

	@Autowired
	private VisitorCardTransactionsRepository repo;
	
	@Override
	protected void apply(VisitorCardTransactions transaction) {
		repo.save(transaction);	
	}

}
