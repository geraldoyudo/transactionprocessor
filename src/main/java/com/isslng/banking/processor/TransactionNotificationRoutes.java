package com.isslng.banking.processor;

import static org.apache.camel.language.spel.SpelExpression.spel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionOutput;

@Component
public class TransactionNotificationRoutes extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("jms:topic:generalTransactionNotification")
		  	.setProperty("transactionInput").spel("#{body.transactionInput}")
		  	.marshal().json(JsonLibrary.Jackson)
	        .recipientList(spel("#{@processorManager.toProcessorUrl(@transactionTypeManager.getNotificationProcessors(exchange.getProperty('transactionInput').code, 'COMPLETED'))}"))
	        .ignoreInvalidEndpoints();
		
		from("jms:topic:generalTransactionNotification")
		  	.setProperty("transactionInput").spel("#{body.transactionInput}")
		  	.marshal().json(JsonLibrary.Jackson)
	        .recipientList(spel("#{@processorManager.toProcessorUrl(@organizationManager.getNotificationProcessors(exchange.getProperty('transactionInput').orgCode, 'COMPLETED'))}"))
	        .ignoreInvalidEndpoints();
		
		from("jms:topic:approvedTransactionNotification")
	  	.setProperty("transactionInput").spel("#{body}")
	  	.marshal().json(JsonLibrary.Jackson)
        .recipientList(spel("#{@processorManager.toProcessorUrl(@transactionTypeManager.getNotificationProcessors(exchange.getProperty('transactionInput').code, 'APPROVED'))}"))
        .ignoreInvalidEndpoints();
	
		from("jms:topic:approvedTransactionNotification")
		  	.setProperty("transactionInput").spel("#{body}")
		  	.marshal().json(JsonLibrary.Jackson)
	        .recipientList(spel("#{@processorManager.toProcessorUrl(@organizationManager.getNotificationProcessors(exchange.getProperty('transactionInput').orgCode, 'APPROVED'))}"))
	        .ignoreInvalidEndpoints();
		
		from("jms:topic:rejectedTransactionNotification")
	  	.setProperty("transactionInput").spel("#{body}")
	  	.marshal().json(JsonLibrary.Jackson)
	    .recipientList(spel("#{@processorManager.toProcessorUrl(@transactionTypeManager.getNotificationProcessors(exchange.getProperty('transactionInput').code, 'REJECTED'))}"))
	    .ignoreInvalidEndpoints();

		from("jms:topic:rejectedTransactionNotification")
		  	.setProperty("transactionInput").spel("#{body}")
		  	.marshal().json(JsonLibrary.Jackson)
		    .recipientList(spel("#{@processorManager.toProcessorUrl(@organizationManager.getNotificationProcessors(exchange.getProperty('transactionInput').orgCode, 'REJECTED'))}"))
		    .ignoreInvalidEndpoints();
		
	}

}
