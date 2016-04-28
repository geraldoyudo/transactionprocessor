package com.isslng.banking.processor.managers;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.Organization;
import com.isslng.banking.processor.entities.Processor;
import com.isslng.banking.processor.entities.TransactionNotification;
import com.isslng.banking.processor.persistence.OrganizationRepository;

@Component
public class OrganizationManager extends BasicRepositoryManager
<OrganizationRepository, Organization, String>{
	
	public Organization getByCode(String code){
		try{
			return managedRepository.findByCode(code).get(0);
		}catch(IndexOutOfBoundsException ex){
			return null;
		}
	}
	
	public Collection<Processor> getNotificationProcessors(String code, String notifyTypeStr){
		TransactionNotification notificationType = TransactionNotification.valueOf(notifyTypeStr);
		Organization org = getByCode(code);
		switch(notificationType){
		case APPROVED:{
			return org.getApprovalNotificationProcessors();
		}
		case COMPLETED: {
			return org.getCompletionNotificationProcessors();
		}
		case REJECTED: {
			return org.getRejectionNotificationProcessors();
		}
		default: return null;
		}
	}
}
