package com.isslng.banking.processor.service;

import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.managers.TransactionInputManager;

@Component
public class ApprovalManager {
	@Autowired
	private TransactionInputManager tiManager;
	
	public TransactionInput approve(@Header("transactionId") String transactionId){
		TransactionInput ti = tiManager.get(transactionId);
		ti.setApproved(true);
		return tiManager.save(ti);
	}
}
