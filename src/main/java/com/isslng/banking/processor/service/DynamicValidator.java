package com.isslng.banking.processor.service;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.exception.TransactionValidatorException;

@Component
public class DynamicValidator {
	@Autowired
	@Qualifier("transactionValidatorSession")
	KieSession transactionValidatorSession;
	
	public TransactionInput validate(TransactionInput ti){
		transactionValidatorSession.insert(ti);
		transactionValidatorSession.fireAllRules();
		Object invalid = ti.getMeta().get("isInvalid");
		if (invalid != null && (((boolean) invalid) == true)){
			throw new TransactionValidatorException((String) ti.getMeta().get("invalidMessage"));
		}
		return ti;
	}
}
