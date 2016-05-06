package com.isslng.banking.processor.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.Message;
import com.isslng.banking.processor.entities.MessageTemplate;

@Component
public class MessageTemplateResolver {
	@Autowired
	private List<MessageTemplateProcessor> messageProcessors;
	
	public Message processTemplate(Map<String,Object> context, MessageTemplate mt){
		for(MessageTemplateProcessor mp: messageProcessors){
			if(mp.supports(mt.getType())){
				return mp.evaluate(context, mt);
			}
		}
		return null;
	}
}
