package com.isslng.banking.processor.service;

import org.apache.camel.Exchange;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.UserChannel;

public interface UserChannelProcessor {
	public boolean supports(String serviceName);
	public String getEndpointUrl(TransactionInput ti, UserChannel userChannel, Exchange exchange);
}
