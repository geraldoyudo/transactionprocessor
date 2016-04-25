package com.isslng.banking.processor;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isslng.banking.processor.persistence.TransactionInput;

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
				return m;
			}
		});
    }
}