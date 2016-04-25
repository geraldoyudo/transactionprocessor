package com.isslng.banking.processor.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

@Component
public class Populator {
	@Autowired
	TransactionTypeRepository ttRepository;
	@Autowired
	ProcessorRepository pRepository;
	@PostConstruct
	public void initRepo(){
		if(ttRepository.findAll().isEmpty()){
			Processor pCash;
			pCash = new Processor();
			pCash.setName("cash-receipt");
			pCash.setUrl("jms:cash-receipt");
			pCash.setDescription("This endpoint processes cash receipt for general use");
			pRepository.save(pCash);
			
			Processor p1 = new Processor();
			p1.setName("sample-processor-1");
			p1.setUrl("jms:topic:processor1");
			p1.setDescription("This tests secondary processing");
            p1 = pRepository.save(p1);
            
            Processor p2 = new Processor();
			p2.setName("sample-processor-2");
			p2.setUrl("jms:topic:processor2");
			p2.setDescription("This tests secondary processing");
		    p2 = pRepository.save(p2);
			
			
			TransactionType t = new TransactionType();
			t.setCode("TR-CASH-RECEIPT");
			t.setPrimaryProcessor(pCash);
			t.setSecondaryProcessors(Sets.newHashSet(p1,p2));
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
