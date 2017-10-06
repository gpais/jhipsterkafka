package com.mycompany.myapp.handlers;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Aggregate;
import com.mycompany.myapp.domain.RetailersTransactions;
import com.mycompany.myapp.domain.enumeration.TransactionStatus;
import com.mycompany.myapp.repository.AggregateRepository;
import com.mycompany.myapp.repository.RetailersTransactionsRepository;

@Service("RetailersTransactionsHandler")
public class RetailersTransactionsHandler extends AbstractTransactionProcessHandler  {

	
	public RetailersTransactionsHandler() {
		super(null);
	}
	@Autowired
	private AggregateRepository aggregateRepo;

	@Autowired
	private RetailersTransactionsRepository repo;
	
	@Override
	protected void apply(RetailersTransactions transaction) {
		Aggregate aggregate = new Aggregate().transaction(transaction);
		aggregateRepo.save(new Aggregate().transaction(transaction));
        List<Aggregate> aggregates = aggregateRepo.findByAggregateId(aggregate.getAggregateId());
        if(aggregates.size() > 1){
        	for(Aggregate currentAggregate:aggregates ){
            	Optional.ofNullable(repo.findRetailersTransactionsByUuid(aggregates.get(0).getData()))
                .ifPresent(a->{
                	a.setStatus(TransactionStatus.DUPLICATED);	
                    repo.save(a);
            	});
        	}

        }else{
            repo.save(transaction);
        }
		  
	}
	
//	public static void main(String args[]) throws IOException{
//		FileWriter writer = new FileWriter("C:\\jhipsterkafkamaster\\jhipsterkafka\\retailersconsumer\\src\\main\\java\\com\\mycompany\\myapp\\handlers\\transactions.dat");
//		   StringBuilder sb = new StringBuilder("");
//	        for (int i=0;i<1000; i++) {
//	        	String value = String.valueOf(i);
//	        	
//	        	sb.append("000000001")
//	        	.append(" ")
//	        	.append("35321321")
//	        	.append("0000".substring(value.length())+ value)
//	        	.append(" ")
//	        	.append("00001")
//	        	.append(" ")
//	        	.append("312312312")
//	        	.append(" ")
//	        	.append("2017")
//	        	.append(" ")
//	        	.append("700")
//	        	.append(" ")
//	        	.append(i>500?1:0)
//	        	.append(" ")
//	        	.append("300")
//	        	.append(" ")
//	        	.append("3")
//	        	.append("\n");
//
//	        }
//	        writer.write(sb.toString());
//	        writer.flush();
//
//	}

}
