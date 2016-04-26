package com.isslng.banking.processor.managers;

import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.persistence.TransactionOutputRepository;

@Component
public class TransactionOutputManager extends  BasicRepositoryManager
<TransactionOutputRepository, TransactionOutput, String> {
	
}
