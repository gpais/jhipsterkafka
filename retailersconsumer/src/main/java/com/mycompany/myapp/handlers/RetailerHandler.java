package com.mycompany.myapp.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Retailers;
import com.mycompany.myapp.domain.RetailersTransactions;
import com.mycompany.myapp.repository.RetailersRepository;


@Service("RetailersHandler")
public class RetailerHandler extends AbstractTransactionProcessHandler {
	Map<String, String> vouchers= new HashMap<>();
	
	
	private RetailersRepository repo;

	@Autowired
	public RetailerHandler(@Qualifier("RetailersCommissionHandler") AbstractTransactionProcessHandler next, RetailersRepository repo){
		super(next);
		this.repo=repo;
	}
	
	
	@Override
	protected void apply(RetailersTransactions transaction) {
		Retailers retailers = repo.findByRetailerNo(transaction.getRetailerNo());
		
		if(retailers != null){
			vouchers.computeIfAbsent(retailers.getRetailerNo(), a->a==null?UUID.randomUUID().toString():a);
			transaction.setVoucherNo(vouchers.get(retailers.getRetailerNo()));
		}
		
		transaction.setRetailers(retailers);
	}


}
