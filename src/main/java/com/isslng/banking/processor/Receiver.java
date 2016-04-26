package com.isslng.banking.processor;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isslng.banking.processor.entities.TransactionInput;

@Component
public class Receiver {
	@Autowired
	JmsOperations jmsOp;
	private String destination = "cash-receipt";
    /**
     * Get a copy of the application context
     */
    @Autowired
    ConfigurableApplicationContext context;
    @Autowired
    ObjectMapper objectMapper;
  

    /**
     * When you receive a message, print it out, then shut down the application.
     * Finally, clean up any ActiveMQ server stuff.
     * @throws IOException 
     */
    
    @JmsListener(destination = "cash-receipt")
    public void reply2(byte[] message ,Message request) throws  JMSException, IOException{
    	
    	System.out.println("Received message");
      	TransactionInput ti = objectMapper.readValue(new String(message), TransactionInput.class);
    	System.out.println(objectMapper.writeValueAsString(ti));
    	jmsOp.send(request.getJMSReplyTo(), new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message m = session.createTextMessage(String.format("Transaction (%s, %s) ", ti.getCode(),
						ti.getUser()));
				m.setJMSCorrelationID(request.getJMSCorrelationID());
				try{
				if( ((int) ti.getTransactionFields().get("amount")) < 500){
					m.setStringProperty("status", "FAILURE");
				}else{
					m.setStringProperty("status", "SUCCESS");
				}
				}catch(Exception ex){
					m.setStringProperty("status", "FAILURE");
			
				}
				return m;
			}
		});
    }
    
    
    @Configuration
   public static class ReceiverConfig {
	   @Bean
	   public ActiveMQTopic processor1(){
		   return new ActiveMQTopic("processor1");
	   }
	   @Bean
	   public ActiveMQTopic processor2(){
		   return new ActiveMQTopic("processor2");
	   }
	   
   }
}