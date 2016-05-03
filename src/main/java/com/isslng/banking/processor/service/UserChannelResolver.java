package com.isslng.banking.processor.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.entities.UserChannel;
import com.isslng.banking.processor.managers.OrganizationManager;

@Component
public class UserChannelResolver {
	private static final String USER_CHANNELS_ITERATOR = "user-channels-iterator";
	@Autowired
	Collection<UserChannelProcessor> userChannelProcessors;
	@Autowired
	OrganizationManager organizationManager;
	
	public String resolve(@ExchangeProperty("transaction") Object transaction, Exchange exchange){
		System.out.println("Resolve method called");
		TransactionInput ti;
		if(transaction instanceof TransactionInput){
			ti = (TransactionInput) transaction;
		}else{
			ti = ((TransactionOutput) transaction).getTransactionInput();
		}
		Object iterValue = exchange.getProperty(USER_CHANNELS_ITERATOR);
		Set<UserChannel> userChannels;
		Iterator<UserChannel> iter;
		if(iterValue == null){
			userChannels = organizationManager.getByCode(ti.getOrgCode()).getUserChannels();
			iter = userChannels.iterator();
		}else{
			iter =  (Iterator<UserChannel>)iterValue;
		}
		
		if(iter.hasNext()){
			UserChannel userChannel = iter.next();
			for(UserChannelProcessor up: userChannelProcessors){
				if(up.supports(userChannel.getNotificationService())){
					System.out.println("Processing Endpoint");
					exchange.setProperty(USER_CHANNELS_ITERATOR, iter);
					return up.getEndpointUrl(ti,userChannel, exchange);
				}
			}
			
		}
		System.out.println("No endpoints left");
		exchange.setProperty(USER_CHANNELS_ITERATOR, null);
		return "";
	}
}
