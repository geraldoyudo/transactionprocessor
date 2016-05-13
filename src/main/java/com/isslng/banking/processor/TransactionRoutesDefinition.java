package com.isslng.banking.processor;

import static org.apache.camel.language.spel.SpelExpression.spel;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.ApprovalInput;
import com.isslng.banking.processor.entities.ExternalResultInput;
import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionNotification;

@Component
public class TransactionRoutesDefinition extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		errorHandler(deadLetterChannel("direct:handleError"));
        from("servlet:///process?httpMethodRestrict=POST")
	        .unmarshal().json(JsonLibrary.Jackson, TransactionInput.class)
	        .to("bean:transactionValidator")
	        .to("bean:droolEvaluator")
	        .to("bean:approvalEvaluator")// drool sets the needsApproval method, if needsApproval, set approved to false
	        //.to("bean:notificationEvaluator") //not implemented
	        .to("bean:transactionInputManager?method=save")
	    .to("direct:processTransaction");
        
        from("servlet:///approve?httpMethodRestrict=POST")
        	.unmarshal().json(JsonLibrary.Jackson, ApprovalInput.class)
	        .to("bean:approvalManager")
	        .to("direct:processTransaction");
        
        from("servlet:///resolve?httpMethodRestrict=POST")
    	.unmarshal().json(JsonLibrary.Jackson, ExternalResultInput.class)
        .to("direct:output");
        
        from("direct:processTransaction")
        	.choice()
	        	.when(spel("#{body.approvalRejected}"))
					.to("direct:processRejectedTransaction")
				.when(spel("#{body.needsApproval && body.approved}"))
					.to("direct:processApprovedTransactionInBackground")	
        		.when(spel("#{body.approved}"))
        			.to("direct:processApprovedTransaction")
        		.otherwise()
        			.to("direct:sendForApproval");
        
        from("direct:processApprovedTransactionInBackground")
	        .wireTap("direct:processApprovedTransaction")
			.to("direct:approvedTransactionProcessing");
        
        from("direct:sendForApproval")
        	.log("Transaction to be approved = ${body.id}")
        	.transform().constant("Transaction has been sent for approval");
        
        from("direct:processExternalTransaction")
	    	.log("External Transaction = ${body.id}")
	    	.marshal().json(JsonLibrary.Jackson);
	    	
        from("direct:processApprovedTransaction")
        	.choice()
        	.when(spel("#{@transactionTypeManager.getPrimaryProcessor(body.code) == null}"))
				.to("bean:transactionInputManager?method=externalize")
				.to("direct:processExternalTransaction")
			.otherwise()
	        	.setProperty("transactionInput").spel("#{body}")
		        .marshal().json(JsonLibrary.Jackson)
		        .setExchangePattern(ExchangePattern.InOut)
		        .recipientList(spel("#{@transactionTypeManager.getPrimaryProcessor"
		        		+ "(exchange.getProperty('transactionInput').code).getUrl()}?jmsMessageType=Object"))
				.to("direct:output")
			.end();
        
        from("direct:output")
	        .to("bean:transactionOutputProcessor")
			.wireTap("direct:secondaryOuptutProcessing")
			.choice()
				.when(spel("#{!exchange.getProperty('transactionInput').needsApproval}"))
					.to("direct:format") //format
				.end();
        		
		from("direct:format")
			.to("bean:transactionOutputProcessor") //format
			.marshal().json(JsonLibrary.Jackson);
        		
            
        from("direct:secondaryOuptutProcessing")
        	.setHeader("notifyType").constant(TransactionNotification.COMPLETED.toString())
	       	.to("seda:notifications");  
        
	    from ("direct:approvedTransactionProcessing")  
	    	.setHeader("notifyType").constant(TransactionNotification.APPROVED.toString())
	    	.wireTap("seda:notifications")
	    	.transform().constant("Transaction successful. User notified");
	    
	    from ("direct:processRejectedTransaction")
	    	.setHeader("notifyType").constant(TransactionNotification.REJECTED.toString())
		    .wireTap("seda:notifications")
	    	.transform().constant("Transaction rejected by issuer.");
	    
	    from("direct:handleError")
	    	.transform().spel("#{exchange.getProperty(Exchange.EXCEPTION_CAUGHT)}")
	    	.wireTap("direct:log")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))	
	    	.to("bean:exceptionDisplay")
	    	.marshal().json(JsonLibrary.Jackson);
	    
	    from("direct:log")  
	    .marshal().json(JsonLibrary.Jackson)
	    .log("${body}");
	    
	}

}
