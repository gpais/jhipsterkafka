package com.mycompany.myapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.channels.ConsumerChannel;
import com.mycompany.myapp.domain.RetailersTransactions;
import com.mycompany.myapp.handlers.RetailerHandler;



@Service
public class ConsumerService {
    private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    private RetailerHandler handler;
    
    @StreamListener(ConsumerChannel.CHANNEL)
    public void consume(RetailersTransactions transaction) {
        log.info("Retailers Transaction  received message: {}.",transaction);
        handler.process(transaction);
    }
}
