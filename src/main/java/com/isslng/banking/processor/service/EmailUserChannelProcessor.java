package com.isslng.banking.processor.service;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.entities.TransactionReference;
import com.isslng.banking.processor.entities.UserChannel;

@Component
public class EmailUserChannelProcessor extends UserChannelProcessor{
	
	@Override
	public boolean supports(String serviceName) {
		return (serviceName!= null && serviceName.equals("email"));
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
		m.setHeader("To", ti.getUserDetails().get("email"));
		m.setHeader("From", userChannel.getProperty("from"));
	}

	@Override
	protected String getBasicEndpoint(TransactionReference tRef, UserChannel userChannel, Exchange exchange) {
		String endpointTemplate = "smtp://%s:%s?username=%s&password=%s";
		String endpoint = String.format(endpointTemplate, 
				userChannel.getProperty("host"),userChannel.getProperty("port"),
				userChannel.getProperty("username"),userChannel.getProperty("password"));
		return endpoint;
	}


	@Override
	protected void onSetupMessage(com.isslng.banking.processor.entities.Message m, Exchange ex) {
		Message in = ex.getIn();
		in.setHeader("Subject", m.getSubject());
		in.setBody(m.getBody(), String.class);
		System.out.println(in.getBody().toString());
	}
	
}
