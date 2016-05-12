package com.isslng.banking.processor.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.ExternalResultInput;
import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.entities.TransactionStatus;
import com.isslng.banking.processor.entities.TransactionType;
import com.isslng.banking.processor.exception.TransactionValidatorException;
import com.isslng.banking.processor.managers.TransactionInputManager;
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
	private TransactionInputManager tiManager;
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
		ti.getOutputRefs().add(to.getId());
		tiManager.save(ti);
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
		ti.getOutputRefs().add(to.getId());
		tiManager.save(ti);
		return to;
	}
	
	public TransactionOutput processOutput(ExternalResultInput extOutput, Exchange exchange){
		TransactionInput ti = tiManager.get(extOutput.getId());
		if( ti.meta("external")== null || !(boolean)ti.meta("external")){
			throw new TransactionValidatorException("Transaction is not an external one");
		}
		if(!ti.isApproved()){
			throw new TransactionValidatorException("Transaction has not been approved");
		}
		if(ti.isApprovalRejected()){
			throw new TransactionValidatorException("Transaction has been rejected earlier");
		}
		ti.meta("processor", extOutput.getProcessor());
		ti = tiManager.save(ti);
		exchange.setProperty("transactionInput", ti);
		return processOutput(extOutput.getOutput(),exchange);
	}
	public TransactionOutputProjection formatOutput(TransactionOutput to){
		return projectionFactory.createProjection(TransactionOutputProjection.class, to);
	}

	
}
