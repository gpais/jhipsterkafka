package com.mycompany.myapp.handlers;

import com.mycompany.myapp.domain.RetailersTransactions;

public abstract class AbstractTransactionProcessHandler {
	private AbstractTransactionProcessHandler next;
	
	public AbstractTransactionProcessHandler(AbstractTransactionProcessHandler next){
		this.next= next;
	}
	
	public void process(RetailersTransactions transaction){
		apply(transaction);
		if(next!= null){
			next.process(transaction);
		}
	}
	  
	protected abstract void apply(RetailersTransactions transaction);

}
