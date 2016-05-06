package com.isslng.banking.processor.service;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.Message;
import com.isslng.banking.processor.entities.MessageTemplate;

@Component
public class VelocityMTProcessor extends MessageTemplateProcessor{
	@Autowired
	private VelocityEngine velocityEngine;
	@Override
	public boolean supports(String messageType) {
		return (messageType.equalsIgnoreCase("velocity"));
	}

	@Override
	protected Message onEvaluate(Map<String,Object> context, MessageTemplate messageTemplate) {
		Context c = new VelocityContext(context);
		StringWriter sw = new StringWriter();
		boolean ok = velocityEngine.evaluate(c, sw, 
				VelocityMTProcessor.class.getName(), messageTemplate.getSubject());
		if(!ok) return null;
		String subject = sw.toString();
		sw = new StringWriter();
		ok = velocityEngine.evaluate(c, sw, 
				VelocityMTProcessor.class.getName(), messageTemplate.getBody());
		if(!ok) return null;
		String body = sw.toString();
		return new Message(subject,body);
	}

}
