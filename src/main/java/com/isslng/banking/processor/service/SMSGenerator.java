package com.isslng.banking.processor.service;

import org.apache.camel.Header;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.SMSMessage;

@Component("smsGenerator")
public class SMSGenerator {
	public SMSMessage generate(String body, @Header("from")String from, 
			@Header("to")String to){
		return new SMSMessage(to, from, body);
	}
}
