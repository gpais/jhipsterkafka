package com.mycompany.myapp.domain.bulk;

import java.util.List;

import com.mycompany.myapp.domain.Transaction;

public class Batch {
	private List<Transaction> transactions;
	private long time;

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	

}
