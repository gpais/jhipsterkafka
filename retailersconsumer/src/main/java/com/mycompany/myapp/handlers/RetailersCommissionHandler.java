package com.mycompany.myapp.handlers;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Commissions;
import com.mycompany.myapp.domain.RetailersTransactions;
import com.mycompany.myapp.repository.CommissionsRepository;



@Service("RetailersCommissionHandler")
public class RetailersCommissionHandler extends AbstractTransactionProcessHandler  {

	private final String FIXED_SHARED_COMMISSION="Shared Commission";
	private CommissionsRepository repo;
	
	
	public RetailersCommissionHandler(
			CommissionsRepository repo,
			@Qualifier("RetailersTransactionsHandler") AbstractTransactionProcessHandler next)
	{
		super(next);
		this.repo=repo;
	}
	
	@Override
	protected void apply(RetailersTransactions transaction ) {
		Optional.ofNullable(repo.findCommissionsBySharedAndRetailers(FIXED_SHARED_COMMISSION))
		.ifPresent(a->{
			Double vattAmount=transaction.getVatAmount()==null?0.0:transaction.getVatAmount();
			BigDecimal retailerCommission=a.getRetailerCommission();
			BigDecimal amount=new BigDecimal(vattAmount).multiply(retailerCommission.divide(new BigDecimal(100)));
			transaction.setRefundAmount(amount.doubleValue());		
		});
	}



}
