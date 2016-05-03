package com.isslng.banking.processor;

import static org.apache.camel.language.spel.SpelExpression.spel;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.ApprovalInput;
import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionNotification;
import com.isslng.banking.processor.exception.TransactionValidatorException;

@Component
public class TransactionRoutesDefinition extends RouteBuilder{

	@Override
	public void configure() throws Exception {
	    
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
        
        from("direct:processApprovedTransaction")
        	.setProperty("transactionInput").spel("#{body}")
	        .marshal().json(JsonLibrary.Jackson)
	        .setExchangePattern(ExchangePattern.InOut)
	        .recipientList(spel("#{@transactionTypeManager.getPrimaryProcessor"
	        		+ "(exchange.getProperty('transactionInput').code).getUrl()}?jmsMessageType=Object"))
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
	       	.to("direct:notifications");  
        
	    from ("direct:approvedTransactionProcessing")  
	    	.setHeader("notifyType").constant(TransactionNotification.APPROVED.toString())
	    	.wireTap("direct:notifications")
	    	.transform().constant("Transaction successful. User notified");
	    
	    from ("direct:processRejectedTransaction")
	    	.setProperty("notifyType").constant(TransactionNotification.REJECTED.toString())
		    .wireTap("direct:notifications")
	    	.transform().constant("Transaction rejected by issuer.");
	
	   
	}

}
