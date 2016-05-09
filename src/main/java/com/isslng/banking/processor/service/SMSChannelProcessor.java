package com.isslng.banking.processor.service;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.entities.TransactionReference;
import com.isslng.banking.processor.entities.UserChannel;
import com.thoughtworks.xstream.core.util.Base64Encoder;

@Component
public class SMSChannelProcessor extends UserChannelProcessor{
	@Override
	public boolean supports(String serviceName) {
		return (serviceName!= null && serviceName.equals("sms"));
	}
	

	@Override
	protected void onSetHeaders(TransactionReference tRef, UserChannel userChannel, Exchange exchange) {
		TransactionInput ti;
		if(tRef instanceof TransactionInput){
			ti = (TransactionInput) tRef;
		}else{
			TransactionOutput to = (TransactionOutput)tRef;
			ti = to.getTransactionInput();
		}
		Message m = exchange.getIn();	
		m.setHeader("to", ti.getUserDetails().get("phone"));
		m.setHeader("from", userChannel.getProperty("from"));
		String username = (String) ti.getUserDetails().get("username");
		String password = (String) ti.getUserDetails().get("password");
		String authentication = String.format("%s:%s", username,password);
		authentication = Base64Utils.encodeToString(authentication.getBytes());
		m.setHeader("Authorization", String.format("Basic %s", authentication));
	}

	@Override
	protected String getBasicEndpoint(TransactionReference tRef, UserChannel userChannel, Exchange exchange) {
		return "seda:sms";
	}


	@Override
	protected void onSetupMessage(com.isslng.banking.processor.entities.Message m, Exchange ex) {
	}
}
