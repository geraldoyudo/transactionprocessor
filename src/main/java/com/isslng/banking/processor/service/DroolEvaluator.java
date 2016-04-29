package com.isslng.banking.processor.service;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;

@Component
public class DroolEvaluator implements TransactionInputEvaluator{
	@Autowired
	@Qualifier("input")
	KieSession droolInputSession;
	
	@Override
	public TransactionInput evaluate(TransactionInput ti) {
		droolInputSession.insert(ti);
		droolInputSession.fireAllRules();
		return ti;
	}

}
