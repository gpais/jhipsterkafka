package com.mycompany.myapp.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

import com.mycompany.myapp.channels.ConsumerChannel;

@EnableBinding(value = {Source.class, ConsumerChannel.class})
public class MessagingConfiguration {

}
