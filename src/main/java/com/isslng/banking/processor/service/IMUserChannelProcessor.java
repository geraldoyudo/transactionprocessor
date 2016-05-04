package com.isslng.banking.processor.service;

import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.UserChannel;

@Component
public class IMUserChannelProcessor implements UserChannelProcessor {
	@Override
	public boolean supports(String serviceName) {
		return (serviceName!= null && serviceName.equals("IM"));
	}

	@Override
	public String getEndpointUrl(TransactionInput ti, UserChannel userChannel, Exchange ex) {
		if(!supports(userChannel.getNotificationService()))
			return "";
		Map<String,String> endpointProperties = userChannel.getEndpointProperties();
		String options = "";
		if(endpointProperties != null && !endpointProperties.isEmpty())
			 options = "&" +  endpointProperties.toString().replace("{", "")
					.replace("}", "").replace(",", "&");
		String endpointTemplate = "xmpp://%s:%s/%s?user=%s&password=%s&serviceName=%s" + options;
		String endpoint = String.format(endpointTemplate, 
				userChannel.getProperty("host"),userChannel.getProperty("port"),
				ti.getUserDetails().get((String) userChannel.getProperty("userIdKey")),
				userChannel.getProperty("username"),userChannel.getProperty("password"),
				userChannel.getProperty("serviceName"));
		System.out.println(endpoint);
		return endpoint;
	}

	
}
