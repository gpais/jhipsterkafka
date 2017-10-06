package com.mycompany.myapp.web.rest;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.myapp.channels.ProducerChannel;
import com.mycompany.myapp.domain.Transaction;
import com.mycompany.myapp.domain.TransactionStatus;
import com.mycompany.myapp.domain.bulk.Batch;
import com.mycompany.myapp.utils.ParseUtils;


@RestController
@RequestMapping("/api")
public class ProducerResource{
    private static final Logger log = LoggerFactory.getLogger(ProducerResource.class);

    private MessageChannel channel;

    public ProducerResource(ProducerChannel channel) {
        this.channel = channel.messageChannel();
    }

    @PostMapping("/transaction")
    public String produce( @RequestBody Transaction transaction) {
    	     UUID uuid = UUID.randomUUID();
    	     transaction.setUuid(uuid.toString());
             channel.send(MessageBuilder.withPayload(transaction).setHeader("correlation_id", uuid.toString()).build());
             return uuid.toString();
    }

    
    @GetMapping("/bulk-transactions")
    public String getBulk() {
    	long start= System.currentTimeMillis(); 

    	Batch bulkTractions=new Batch();
    	bulkTractions.setTransactions(new ArrayList<>());
        for(int i=0;i<10000;i++){
        	Transaction transaction = new Transaction();
        	transaction.setCreditCardNo("23123122121321");
        	transaction.setCreditCardExpiry("10-2017");
//        	transaction.setPurchaseDate(ZonedDateTime.now());
        	transaction.setUuid(UUID.randomUUID().toString());
        	transaction.setGrossAmount(100.00);
        	transaction.setRefundAmount(10.00);
        	transaction.setStatus(TransactionStatus.NOT_MATCHED);
        	transaction.setTfscNumber("23123122121321");
        	transaction.setRetailerNo("23123122121321");
        	transaction.setVoucherNo("23123122121321");
        	transaction.setReason("just a test");
        	bulkTractions.getTransactions().add(transaction);
            channel.send(MessageBuilder.withPayload(transaction).setHeader("correlation_id", transaction.getUuid()).build());

        }
    	long time=System.currentTimeMillis()-start;
    	return "success ["+ time+"] ms";  
    	
    }



    @RequestMapping(value = "/transactionsFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("uploadfile") MultipartFile uploadfile) {
        log.info("Single file upload!");

        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
      try {
    	 ParseUtils.parse(uploadfile)
    	  .map(line->line.split(" "))
    	  .map(splitedLine->{
  	        log.info("Splited line parsed !" +splitedLine);

    		  Transaction transaction = new Transaction();
    		  transaction.setUuid(UUID.randomUUID().toString());
    		  transaction.setRetailerNo(splitedLine[0]);
    		  transaction.setCreditCardNo(splitedLine[1]);
    		  transaction.setTfscNumber(splitedLine[2]);
    		  transaction.setVoucherNo(splitedLine[3]);
    		  transaction.setCreditCardExpiry(splitedLine[4]);
    		  transaction.setPurchaseTime(splitedLine[5]);
    		  transaction.setVatOff(splitedLine[6]);
    		  transaction.setGrossAmount(Double.valueOf(splitedLine[7]));
    		  transaction.setVatAmount(Double.valueOf(splitedLine[8]));
    		  return transaction;
    		  
    	  })
    	 .forEach(a->{
             channel.send(MessageBuilder.withPayload(a).setHeader("correlation_id", a.getUuid()).build());

    	 });
//    	 byte[] bytes = uploadfile
        // Get the filename and build the local file path
        String filename = uploadfile.getOriginalFilename();
//        String directory = env.getProperty("netgloo.paths.uploadedFiles");
//        String filepath = Paths.get(directory, filename).toString();
//        
//        // Save the file locally
//        BufferedOutputStream stream =
//            new BufferedOutputStream(new FileOutputStream(new File(filepath)));
//        stream.write(uploadfile.getBytes());
//        stream.close();
        return new ResponseEntity<>(HttpStatus.OK);

      }
      catch (Exception e) {
        System.out.println(e.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      
    }
    
    

    
    @PutMapping("/transactions")
    public String updateTransaction(@RequestBody Batch transactions) throws URISyntaxException {
    	long start= System.currentTimeMillis(); 
    	transactions.getTransactions().forEach(a->{
            channel.send(MessageBuilder.withPayload(a).setHeader("correlation_id", a.getUuid()).build());

    	});
    	long time=System.currentTimeMillis()-start;
    	return "success ["+ time+"] ms";
    }
  

    
}
