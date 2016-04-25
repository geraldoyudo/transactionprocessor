package com.isslng.banking.processor;

import static org.apache.camel.language.spel.SpelExpression.spel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.persistence.TransactionInput;

@Component
public class TransactionRoutesDefinition extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// Access us using http://localhost:8080/camel/hello
        from("servlet:///process?httpMethodRestrict=POST")
        .unmarshal().json(JsonLibrary.Jackson, TransactionInput.class)
        .to("bean:transactionValidator")
        .to("bean:transactionInputManager?method=save")
        .setProperty("transactionInput").spel("#{body}")
        .recipientList(spel("jms:#{@transactionTypeManager.getPrimaryProcessor"
        		+ "(request.body.code)}?jmsMessageType=Object"))
		.to("bean:transactionOutputProcessor")
		.to("bean:transactionOutputProcessor") //format
		.marshal().json(JsonLibrary.Jackson);
	}

}
