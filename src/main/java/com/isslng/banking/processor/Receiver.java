package com.isslng.banking.processor;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

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
import com.isslng.banking.processor.persistence.TransactionInput;
import com.isslng.banking.processor.persistence.TransactionOutput;

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
     * @throws JsonProcessingException 
     */
    
    @JmsListener(destination = "cash-receipt")
    public void reply2(Message request) throws  JMSException, JsonProcessingException{
    	
    	System.out.println("Received message");
    	TransactionInput ti = (TransactionInput)((ObjectMessage) request).getObject();
    	System.out.println(objectMapper.writeValueAsString(ti));
    	jmsOp.send(request.getJMSReplyTo(), new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message m = session.createTextMessage("Hello from " + destination);
				m.setJMSCorrelationID(request.getJMSCorrelationID());
				if( ((int) ti.getTransactionFields().get("amount")) < 500){
					m.setStringProperty("status", "FAILURE");
				}else{
					m.setStringProperty("status", "SUCCESS");
				}
					
				return m;
			}
		});
    }
    
    @JmsListener(destination = "processor1")
    public void reply1(Message request) throws  JMSException, JsonProcessingException{
    	
    	System.out.println("Received message from processor1");
    	TransactionOutput ti = (TransactionOutput)((ObjectMessage) request).getObject();
    	System.out.println(objectMapper.writeValueAsString(ti));
    	
    }
    
    @JmsListener(destination = "processor2")
    public void reply(Message request) throws  JMSException, JsonProcessingException{
    	
    	System.out.println("Received message from processor2");
    	TransactionOutput ti = (TransactionOutput)((ObjectMessage) request).getObject();
    	System.out.println(objectMapper.writeValueAsString(ti));
    	
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