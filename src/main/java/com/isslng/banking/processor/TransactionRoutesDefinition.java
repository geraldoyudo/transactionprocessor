package com.isslng.banking.processor;

import static org.apache.camel.language.spel.SpelExpression.spel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.ApprovalInput;
import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.exception.TransactionValidatorException;

@Component
public class TransactionRoutesDefinition extends RouteBuilder{

	@Override
	public void configure() throws Exception {
	    
        from("servlet:///process?httpMethodRestrict=POST")
	        .unmarshal().json(JsonLibrary.Jackson, TransactionInput.class)
	        .to("bean:transactionValidator")
	        .to("bean:approvalEvaluator")// drool sets the needsApproval method, if needsApproval, set approved to false
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
        		.when(spel("#{body.approved}"))
        			.to("direct:processApprovedTransaction")
        		.otherwise()
        			.to("direct:sendForApproval");
        
        from("direct:sendForApproval")
        	.log("Transaction to be approved = ${body.id}")
        	.transform().constant("Transaction has been sent for approval");
        
        from("direct:processApprovedTransaction")
        	.setProperty("transactionInput").spel("#{body}")
	        .marshal().json(JsonLibrary.Jackson)
	        .recipientList(spel("#{@transactionTypeManager.getPrimaryProcessor"
	        		+ "(exchange.getProperty('transactionInput').code).getUrl()}?jmsMessageType=Object"))
			.to("bean:transactionOutputProcessor")
			.wireTap("direct:secondaryOuptutProcessing")
			.to("bean:transactionOutputProcessor") //format
			.marshal().json(JsonLibrary.Jackson)
			.choice()
				.when(spel("#{exchange.getProperty('transactionInput').needsApproval}"))
					.to("direct:approvedTransactionProcessing");
        		
            
        from("direct:secondaryOuptutProcessing")
        	.marshal().json(JsonLibrary.Jackson)
	        .multicast()
	        .to("direct:generalOutputProcessing", "direct:specificOutputProcessing");
	        
	    from("direct:specificOutputProcessing")
	        .log("Secondary processing")
	        .recipientList(spel("#{@processorManager.toProcessorUrl(@transactionTypeManager.getSecondaryProcessors(exchange.getProperty('transactionInput').code))}"))
	        .ignoreInvalidEndpoints();
	    
	    from("direct:generalOutputProcessing")
	    	.to("jms:generalTransactionNotification");
	    
	    from ("direct:approvedTransactionProcessing")
	    	.wireTap("jms:approvedTransactionNotification")
	    	.transform().constant("Transaction successful. User notified");
	    from ("direct:processRejectedTransaction")
	    	.marshal().json(JsonLibrary.Jackson)
		    .wireTap("jms:rejectedTransactionNotification")
	    	.transform().constant("Transaction rejected by issuer.");
	    
	
	   
	}

}
