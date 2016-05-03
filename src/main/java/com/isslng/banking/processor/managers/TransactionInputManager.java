package com.isslng.banking.processor.managers;

import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.persistence.TransactionInputRepository;

@Component
public class TransactionInputManager extends BasicRepositoryManager<TransactionInputRepository,
TransactionInput, String>{
	
}
