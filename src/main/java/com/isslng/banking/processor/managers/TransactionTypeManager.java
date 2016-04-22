package com.isslng.banking.processor.managers;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.isslng.banking.processor.persistence.TransactionType;
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
	public String getPrimaryProcessor(String code){
		TransactionType tt = getByCode(code);
		return tt.getPrimaryProcessor();
	}
	
	public Set<String> getSecondaryProcessors(String code){
		TransactionType tt = getByCode(code);
		return tt.getSecondaryProcessors();
	}
}
