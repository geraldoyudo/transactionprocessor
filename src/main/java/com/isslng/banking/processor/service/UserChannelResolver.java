package com.isslng.banking.processor.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperty;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.entities.UserChannel;
import com.isslng.banking.processor.managers.OrganizationManager;

@Component
public class UserChannelResolver {
	private static final String USER_CHANNELS_ITERATOR = "user-channels-iterator";
	private static final String USER_CHANNEL = "user-channel";
	@Autowired
	Collection<UserChannelProcessor> userChannelProcessors;
	@Autowired
	OrganizationManager organizationManager;
	
	
	public String resolve(@ExchangeProperty("transaction") Object transaction, Exchange exchange,
			@Header(Exchange.SLIP_ENDPOINT) String previous, @Header("notifyType") String notifyType) {
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
			Object ucObject = ti.meta("userChannels");
			if(ucObject != null){
				Map<String, List<String>> channelMap = (Map<String,List<String>>)ucObject;
				Object channelObj = channelMap.get(notifyType);
				if(channelObj != null){
					List<String> acceptedChannels = (List<String>)channelObj;
					List<UserChannel> channels = new ArrayList<>(userChannels);
					for(UserChannel c: channels){
						if(!acceptedChannels.contains(c.getName())){
							userChannels.remove(c);
						}
							
					}
				}
			}
			iter = userChannels.iterator();
		}else{
			iter =  (Iterator<UserChannel>)iterValue;
		}
		
		UserChannel userChannel;
		if(iter.hasNext() &&(previous == null || !previous.contains("language:"))){
			
			userChannel = iter.next();
			System.out.println("Modifying message body");
			for(UserChannelProcessor up: userChannelProcessors){
				if(up.supports(userChannel.getNotificationService())){
					System.out.println("Processing message for Endpoint");		
					if(transaction instanceof TransactionInput)
						up.setUpChannel((TransactionInput) transaction,userChannel, exchange);
					else{
						up.setUpChannel((TransactionOutput) transaction,userChannel, exchange);
					}
					exchange.setProperty(USER_CHANNELS_ITERATOR, iter);
					exchange.setProperty(USER_CHANNEL, userChannel);
					return "language:simple:${header.messageBody}";
				}
			}
		}else if(previous.contains("language:")){
			userChannel = (UserChannel) exchange.getProperty(USER_CHANNEL);
			for(UserChannelProcessor up: userChannelProcessors){
				if(up.supports(userChannel.getNotificationService())){
					System.out.println("Processing Endpoint");
					if(transaction instanceof TransactionInput)
						return up.getEndpointUrl((TransactionInput) transaction,userChannel, exchange);
					else{
						return up.getEndpointUrl((TransactionOutput) transaction,userChannel, exchange);
					}
				}
			}
		}
		System.out.println("No endpoints left");
		exchange.setProperty(USER_CHANNELS_ITERATOR, null);
		
		
		
		return "";
	}
}
