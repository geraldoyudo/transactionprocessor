package com.isslng.banking.processor.service;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.Message;
import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.entities.TransactionReference;
import com.isslng.banking.processor.entities.UserChannel;

@Component
public class IMUserChannelProcessor extends UserChannelProcessor {
	@Override
	public boolean supports(String serviceName) {
		return (serviceName!= null && serviceName.equals("IM"));
	}

	@Override
	protected void onSetHeaders(TransactionReference tRef, UserChannel userChannel, Exchange exchange) {
		// no headers to set
		
	}

	@Override
	protected String getBasicEndpoint(TransactionReference tRef, UserChannel userChannel, Exchange exchange) {
		TransactionInput ti;
		if(tRef instanceof TransactionInput){
			ti = (TransactionInput) tRef;
		}else{
			TransactionOutput to = (TransactionOutput)tRef;
			ti = to.getTransactionInput();
		}
		String endpointTemplate = "xmpp://%s:%s/%s?user=%s&password=%s&serviceName=%s";
		String endpoint = String.format(endpointTemplate, 
				userChannel.getProperty("host"),userChannel.getProperty("port"),
				ti.getUserDetails().get((String) userChannel.getProperty("userIdKey")),
				userChannel.getProperty("username"),userChannel.getProperty("password"),
				userChannel.getProperty("serviceName"));
		
		return endpoint;
	}

	@Override
	protected void onSetupMessage(Message m, Exchange exchange) {
		exchange.getIn().setBody(m.getBody());
	}

	
}
