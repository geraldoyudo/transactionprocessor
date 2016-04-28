package com.isslng.banking.processor.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.entities.TransactionStatus;
import com.isslng.banking.processor.entities.TransactionType;
import com.isslng.banking.processor.managers.TransactionOutputManager;
import com.isslng.banking.processor.managers.TransactionTypeManager;
import com.isslng.banking.processor.persistence.TransactionOutputProjection;

@Component
public class TransactionOutputProcessor {
	@Autowired
	private TransactionTypeManager ttManager;
	@Autowired
	private TransactionOutputManager toManager;
	@Autowired
    private ProjectionFactory projectionFactory;
	
	public TransactionOutput processOutput(String output, Exchange exchange){
		TransactionInput ti = (TransactionInput) exchange.getProperty("transactionInput");
		TransactionType tt = ttManager.getByCode(ti.getCode());
		Map<String, Boolean> outputFields = tt.getOutputFields();
		TransactionOutput to = new TransactionOutput();
		to.setTransactionInput(ti);
		Object obj = exchange.getIn().getHeader("status");
		if(obj == null){
			to.setStatus(TransactionStatus.SUCCESS);
		}else{
			 to.setStatus(TransactionStatus.valueOf( (String)obj));
		}
	   
		Map<String, Object> outMap = new HashMap<>();
		for(String key: outputFields.keySet()){
			if(outputFields.get(key)) // autofill field
				outMap.put(key, output);
		}
		to.setOutputFields(outMap);
		to = toManager.save(to);
		return to;
	}
	public TransactionOutput processOutput(Map<String,Object> output,  Exchange exchange){
		TransactionInput ti = (TransactionInput) exchange.getProperty("transactionInput");
		TransactionType tt = ttManager.getByCode(ti.getCode());
		Map<String, Boolean> outputFields = tt.getOutputFields();
		TransactionOutput to = new TransactionOutput();
		to.setTransactionInput(ti);
		try{
		to.setStatus(TransactionStatus.valueOf( (String)exchange.getIn().getHeader("status")));
		}catch(NullPointerException ex){
			to.setStatus(TransactionStatus.SUCCESS);
		}
		
		for(String key: outputFields.keySet()){
			if(!output.containsKey(key)){
				if(outputFields.get(key)){ //autofill field
					output.put(key, "");
				}
			}
		}
		to.setOutputFields(output);
		to = toManager.save(to);
		return to;
	}
	public TransactionOutputProjection formatOutput(TransactionOutput to){
		return projectionFactory.createProjection(TransactionOutputProjection.class, to);
	}
	
}
