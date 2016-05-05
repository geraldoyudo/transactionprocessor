package com.isslng.banking.processor.service;

import java.util.Map;

import com.isslng.banking.processor.entities.Message;
import com.isslng.banking.processor.entities.MessageTemplate;

public abstract class MessageTemplateProcessor {
	public abstract boolean supports(String messageType);
	protected abstract Message onEvaluate(Map<String,Object> context, MessageTemplate messageTemplate);
	
	public final Message evaluate(Map<String, Object> context, MessageTemplate messageTemplate){
		if(supports(messageTemplate.getType())){
			return onEvaluate(context, messageTemplate);
		}
		return null;
	}
}
