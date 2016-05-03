package com.isslng.banking.processor.service;

import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;

@Component
public class UserNotificationTemplateManager {
	public String getUserTemplate(TransactionInput ti){
		return "velocity ==> ${body}";
	}
}
