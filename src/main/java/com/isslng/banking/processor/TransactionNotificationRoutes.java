package com.isslng.banking.processor;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.velocity.Template;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.service.UserChannelResolver;

@Component
public class TransactionNotificationRoutes extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("jms:notifications")
			.log("${headers.notifyType} ${body}")
			.recipientList().spel("#{@notificationSlipManager.getNotificationSlips"
					+ "(body,request.headers['notifyType'])}").ignoreInvalidEndpoints();
		from("jms:user-notification")
			.setProperty("transaction").spel("#{body}")
			.marshal().json(JsonLibrary.Jackson)
			.log("USER ${headers.notifyType} ${body}")
			.dynamicRouter(method(UserChannelResolver.class, "resolve"));
	
		from("jms:type-notification")
			.setProperty("transaction").spel("#{body}")
		  	.marshal().json(JsonLibrary.Jackson)
	        .recipientList().spel("#{@processorManager.toProcessorUrl"
	        		+ "(@transactionTypeManager.getNotificationProcessors"
	        		+ "(T(com.isslng.banking.processor.managers.TransactionTypeManager).getTransactionCode"
	        		+ "(exchange.getProperty('transaction')), "
	        		+ "request.headers['notifyType']))}")
	       .ignoreInvalidEndpoints();
		
		from("jms:tenant-notification")
			.log("TENANT ${headers.notifyType} ${body}")
			.setProperty("transaction").spel("#{body}")
		  	.marshal().json(JsonLibrary.Jackson)
	        .recipientList().spel("#{@processorManager.toProcessorUrl"
	        		+ "(@organizationManager.getNotificationProcessors"
	        		+ "(T(com.isslng.banking.processor.managers.TransactionTypeManager).getTenantCode"
	        		+ "(exchange.getProperty('transaction')), "
	        		+ "request.headers['notifyType']))}")
	       .ignoreInvalidEndpoints();
	}

}