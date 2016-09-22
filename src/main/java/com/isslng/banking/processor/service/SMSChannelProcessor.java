package com.isslng.banking.processor.service;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.entities.TransactionReference;
import com.isslng.banking.processor.entities.UserChannel;

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
		String username = (String) userChannel.getProperty("username");
		String password = (String) userChannel.getProperty("password");
		String authentication = String.format("%s:%s", username,password);
		System.out.println(authentication);
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


	@Override
	protected boolean isValid(TransactionInput ti, UserChannel channel) {
		Map<String,Object> userDetails = ti.getUserDetails();
		if(channel == null)
			return false;
		if(channel.getProperty("username") == null || channel.getProperty("password") == null || channel.getProperty("from") == null)
			return false;
		if(userDetails == null || userDetails.get("phone") == null)
			return false;
		return true;
	}
}
