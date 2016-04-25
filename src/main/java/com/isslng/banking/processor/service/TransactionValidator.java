package com.isslng.banking.processor.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.exception.TransactionValidatorException;
import com.isslng.banking.processor.managers.TransactionTypeManager;
import com.isslng.banking.processor.persistence.TransactionInput;
import com.isslng.banking.processor.persistence.TransactionType;

@Component
public class TransactionValidator {
	@Autowired
	TransactionTypeManager ttManager;
	public TransactionInput validate(TransactionInput ti){
		String code = ti.getCode();
		if(code == null || code.isEmpty()) 
			throw new TransactionValidatorException("No transaction code");
		TransactionType transactionType = ttManager.getByCode(code);
		if(transactionType == null)
			throw new TransactionValidatorException("Transaction code not supported");
		Map<String, Boolean> fields = transactionType.getInputFields();
		Map<String, Object> transactionFields = ti.getTransactionFields();
		boolean mandatory;
		Object value;
		for(String field: fields.keySet()){
			mandatory = fields.get(field);
			if(mandatory){
				try{
				value = transactionFields.get(field);
				if(value == null || value.equals("")){
					throw new TransactionValidatorException(
							String.format("Mandatory field %s is null or empty", field));
				}
				}catch(NullPointerException ex){
					throw new TransactionValidatorException(
							String.format("Mandatory field %s is null or empty", field));
				}
			}
		}
		return ti;
		
	}
}
