package com.mycompany.myapp.handlers;

import com.mycompany.myapp.domain.VisitorCardTransactions;

public abstract class AbstractTransactionProcessHandler {
	private AbstractTransactionProcessHandler next;
	
	public AbstractTransactionProcessHandler(AbstractTransactionProcessHandler next){
		this.next= next;
	}
	
	public void process(VisitorCardTransactions transaction){
		apply(transaction);
		if(next!= null){
			next.process(transaction);
		}
	}
	  
	protected abstract void apply(VisitorCardTransactions transaction);

}
