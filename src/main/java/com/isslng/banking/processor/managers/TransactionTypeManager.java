package com.isslng.banking.processor.managers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.Processor;
import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionNotification;
import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.entities.TransactionType;
import com.isslng.banking.processor.persistence.TransactionTypeRepository;

@Component
public class TransactionTypeManager extends BasicRepositoryManager
<TransactionTypeRepository, TransactionType, String>{	
	public TransactionType getByCode(String code){
		try{
			return managedRepository.findByCode(code).get(0);
		}catch(IndexOutOfBoundsException ex){
			return null;
		}
	}
	public Processor getPrimaryProcessor(String code){
		TransactionType tt = getByCode(code);
		return tt.getPrimaryProcessor();
	}
	
	public Set<Processor> getNotificationProcessors(String code, String notifyTypeStr){
		TransactionNotification notifyType = TransactionNotification.valueOf(notifyTypeStr);
		TransactionType tt = getByCode(code);
		switch(notifyType){
			case APPROVED:{
				return tt.getApprovalNotificationProcessors();
			}
			case COMPLETED:{
				return tt.getCompletionNotificationProcessors();
			}
			case REJECTED:{
				return tt.getRejectionNotificationProcessors();
			}
			default:return new HashSet<>();
		}
		
	}
	
	public static String getTransactionCode(TransactionInput ti){
		return ti.getCode();
	}
	public static String getTransactionCode(TransactionOutput to){
		return to.getTransactionInput().getCode();
	}
	public static String getTenantCode(TransactionInput ti){
		return ti.getOrgCode();
	}
	public static String getTenantCode(TransactionOutput to){
		return to.getTransactionInput().getOrgCode();
	}
}
