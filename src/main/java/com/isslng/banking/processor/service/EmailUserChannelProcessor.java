package com.isslng.banking.processor.service;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.UserChannel;

@Component
public class EmailUserChannelProcessor implements UserChannelProcessor{

	@Override
	public boolean supports(String serviceName) {
		return (serviceName!= null && serviceName.equals("email"));
	}

	@Override
	public String getEndpointUrl(UserChannel userChannel) {
		if(!supports(userChannel.getNotificationService()))
			return "";
		String endpointTemplate = "smtp://%s:%s?username=%s&password=%s&mail.smtp.starttls.enable=true";
		String endpoint = String.format(endpointTemplate, 
				userChannel.getProperty("host"),userChannel.getProperty("port"),
				userChannel.getProperty("username"),userChannel.getProperty("password"));
		System.out.println(endpoint);
		return endpoint;
	}

	@Override
	public void setHeaders(TransactionInput ti, UserChannel userChannel, Exchange exchange) {
		// TODO Auto-generated method stub
		Message m = exchange.getIn();	
		m.setHeader("To", ti.getUserDetails().get("email"));
		m.setHeader("Subject", userChannel.getProperty("subject"));
		m.setHeader("From", userChannel.getProperty("from"));
	}
	
}
