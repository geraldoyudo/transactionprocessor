package com.isslng.banking.processor.service;

import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;

@Component
public class ApprovalEvaluator implements TransactionInputEvaluator{
	
	public TransactionInput evaluate(TransactionInput ti){
		if(ti.getNeedsApproval()||ti.isApprovalRejected()){
			ti.setApproved(false);
		}
		else{
			ti.setApproved(true);
		}
		return ti;
	}
}
