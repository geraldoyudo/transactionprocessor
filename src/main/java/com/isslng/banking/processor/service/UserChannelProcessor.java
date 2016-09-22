package com.isslng.banking.processor.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;

import com.isslng.banking.processor.entities.Message;
import com.isslng.banking.processor.entities.MessageTemplate;
import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionNotification;
import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.entities.TransactionReference;
import com.isslng.banking.processor.entities.UserChannel;
import com.isslng.banking.processor.managers.MTAManager;

public abstract class UserChannelProcessor {
	@Autowired
	private MTAManager mtaManager;
	@Autowired
	private MessageTemplateResolver mtResolver;
	public static final String DEAD_QUEUE = "invalid";
	
	public abstract boolean supports(String serviceName);
	
	protected abstract void onSetHeaders(TransactionReference tRef, UserChannel userChannel, Exchange exchange);
	protected abstract String getBasicEndpoint(TransactionReference tRef, UserChannel userChannel, Exchange exchange);
	protected abstract void onSetupMessage(Message m, Exchange ex);
	public final String getEndpointUrl(TransactionReference tRef, UserChannel userChannel, Exchange exchange){
		onSetHeaders(tRef,userChannel, exchange);
		String url =  evaluateEnpointUrl(tRef, userChannel, exchange);
		System.out.println(url);
		return url;
	}
	public final void setUpChannel(TransactionReference tRef, UserChannel userChannel, Exchange exchange){
		setUpMessage(tRef,userChannel, exchange);
	}
	private void setUpMessage(TransactionReference tRef, UserChannel userChannel, Exchange exchange){
		TransactionInput ti;
		Map<String, Object> context = new HashMap<>();
		if(tRef instanceof TransactionInput){
			ti = (TransactionInput) tRef;
			context.put("ti", ti);
		}else{
			TransactionOutput to = (TransactionOutput)tRef;
			ti = to.getTransactionInput();
			context.put("ti", ti);
			context.put("to", to);
		}
		MessageTemplate mt = mtaManager.getMessageTemplate(userChannel.getName(), 
				ti.getOrgCode(), ti.getCode(), TransactionNotification.valueOf(
						(String) exchange.getIn().getHeader("notifyType")));
		Message m = mtResolver.processTemplate(context, mt);
		onSetupMessage(m, exchange);
		exchange.getIn().setHeader("messageBody", m.getBody());
	}
	
	private String evaluateEnpointUrl(TransactionReference tRef, UserChannel userChannel, Exchange exchange){
		if(!isValid(getTransactionInput(tRef), userChannel))
			return "invalid";
		String basicEndpoint = getBasicEndpoint(tRef, userChannel, exchange);
		
		Map<String,String> endpointProperties = userChannel.getEndpointProperties();
		String options = "";
		if(endpointProperties != null && !endpointProperties.isEmpty())
			 options = "&" +  endpointProperties.toString().replace("{", "")
					.replace("}", "").replace(",", "&");
		
		String endpoint = basicEndpoint + options;
		
		return endpoint;
	}
	
	protected abstract boolean isValid(TransactionInput ti, UserChannel channel);
	protected static TransactionInput getTransactionInput(TransactionReference tRef ){
		TransactionInput ti;
		if(tRef instanceof TransactionInput){
			ti = (TransactionInput) tRef;
		}else{
			TransactionOutput to = (TransactionOutput)tRef;
			ti = to.getTransactionInput();
		}
		return ti;
	}
}
