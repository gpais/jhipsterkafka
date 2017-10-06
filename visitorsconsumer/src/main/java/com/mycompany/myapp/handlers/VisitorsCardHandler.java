package com.mycompany.myapp.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.VisitorCard;
import com.mycompany.myapp.domain.VisitorCardTransactions;
import com.mycompany.myapp.repository.VisitorCardRepository;

@Service("VisitorsCardHandler")
public class VisitorsCardHandler extends AbstractTransactionProcessHandler {
	
	
	private VisitorCardRepository repo;

	@Autowired
	public VisitorsCardHandler(@Qualifier("VisitorsTransactionHandler") AbstractTransactionProcessHandler next, VisitorCardRepository repo){
		super(next);
		this.repo=repo;
	}
	@Override
	protected void apply(VisitorCardTransactions transaction) {
		VisitorCard visitorCard = repo.findByTfscNumber(transaction.getTfscNumber());
		transaction.setVisitorCard(visitorCard);
	}

}
