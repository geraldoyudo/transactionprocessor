package com.isslng.banking.processor.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Populator {
	@Autowired
	TransactionTypeRepository ttRepository;
	@PostConstruct
	public void initRepo(){
		if(ttRepository.findAll().isEmpty()){
			TransactionType t = new TransactionType();
			t.setCode("TR-CASH-RECEIPT");
			t.setPrimaryProcessor("cash-receipt");
			Map<String,Boolean> outputFields= new HashMap<>();
			outputFields.put("message", true);
			Map<String,Boolean> inputFields= new HashMap<>();
			inputFields.put("amount", true);
			
			t.setOutputFields(outputFields);
			t.setInputFields(inputFields);
			ttRepository.save(t);
		}
		
	}
}
