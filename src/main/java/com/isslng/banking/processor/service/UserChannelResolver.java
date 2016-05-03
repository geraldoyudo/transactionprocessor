package com.isslng.banking.processor.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.entities.UserChannel;

@Component
public class UserChannelResolver {
	@Autowired
	Collection<UserChannelProcessor> userChannelProcessors;
	
	public String resolve(TransactionInput ti, Set<UserChannel> userChannels, Exchange exchange){
		Iterator<UserChannel> iter = userChannels.iterator();
		if(iter.hasNext()){
			UserChannel userChannel = iter.next();
			for(UserChannelProcessor up: userChannelProcessors){
				if(up.supports(userChannel.getNotificationService())){
					up.setHeaders(ti, userChannel, exchange);
					return up.getEndpointUrl(userChannel);
				}
			}
		}
		
		return "";
	}
	public String resolve(TransactionOutput to, Set<UserChannel> userChannels, Exchange exchange){
		return resolve(to.getTransactionInput(), userChannels,exchange);
	}
}
