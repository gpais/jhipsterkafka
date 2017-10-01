package com.mycompany.myapp.web.rest;

import java.util.UUID;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.channels.ProducerChannel;
import com.mycompany.myapp.domain.Transaction;

@RestController
@RequestMapping("/api")
public class ProducerResource{

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

}
