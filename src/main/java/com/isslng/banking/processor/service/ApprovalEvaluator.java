package com.isslng.banking.processor.service;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;

@Component
public class ApprovalEvaluator {
	@Autowired
	@Qualifier("approvalEvaluatorSession")
	KieSession approvalEvaluatorSession;
	
	public TransactionInput evaluate(TransactionInput ti){
		approvalEvaluatorSession.insert(ti);
		approvalEvaluatorSession.fireAllRules();
		if(ti.getNeedsApproval()){
			ti.setApproved(false);
		}else{
			ti.setApproved(true);
		}
		return ti;
	}
}
