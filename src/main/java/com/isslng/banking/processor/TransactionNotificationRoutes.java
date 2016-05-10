package com.isslng.banking.processor;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.velocity.Template;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.service.UserChannelResolver;

@Component
public class TransactionNotificationRoutes extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("seda:notifications")
			.log("${headers.notifyType} ${body}")
			.recipientList().spel("#{@notificationSlipManager.getNotificationSlips"
					+ "(body,request.headers['notifyType'])}").ignoreInvalidEndpoints();
		from("seda:user-notification")
			.setProperty("transaction").spel("#{body}")
			.dynamicRouter(  method(UserChannelResolver.class, "resolve"));
	
		from("seda:type-notification")
			.setProperty("transaction").spel("#{body}")
		  	.marshal().json(JsonLibrary.Jackson)
	        .recipientList().spel("#{@processorManager.toProcessorUrl"
	        		+ "(@transactionTypeManager.getNotificationProcessors"
	        		+ "(T(com.isslng.banking.processor.managers.TransactionTypeManager).getTransactionCode"
	        		+ "(exchange.getProperty('transaction')), "
	        		+ "request.headers['notifyType']))}")
	       .ignoreInvalidEndpoints();
		
		from("seda:tenant-notification")
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
