package com.isslng.banking.processor.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.ApprovalInput;
import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.managers.TransactionInputManager;

@Component
public class ApprovalManager {
	@Autowired
	private TransactionInputManager tiManager;
	
	public TransactionInput processApproval(ApprovalInput approval){
		TransactionInput ti = tiManager.get(approval.getId());
		if(ti.isApprovalRejected() || ti.isApproved()){
			throw new NullPointerException("Transaction to approve not found");
		}
		int numberOfApprovers = updateApprovalList(approval, ti);
		if(approval.isRejected()){
			ti.setApprovalRejected(true);
			return tiManager.save(ti);
		}
		Object approvalSizeVal = ti.meta("approvalSize");
		if(approvalSizeVal == null){
			ti.setApproved(true);
		}else{
			int approvalSize = (int) approvalSizeVal;
			ti.setApproved(numberOfApprovers >= approvalSize);
		}
		
		return tiManager.save(ti);
	}

	private int updateApprovalList(ApprovalInput approval, TransactionInput ti) {
		Object approvalManagers = ti.meta("approvalList");
		Set<String> approvalList = new HashSet<String>();
		if(approvalManagers != null){
			approvalList.addAll((Collection<String>) approvalManagers);		
		}
		approvalList.add(approval.getUser());
		ti.meta("approvalList", approvalList);
		return approvalList.size();
	}
	
}
