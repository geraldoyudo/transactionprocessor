package com.isslng.banking.processor.entities;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TransactionOutput extends Resource {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3335072092623079083L;

	@DBRef
	private TransactionInput transactionInput;
	private TransactionStatus status;
	private Map<String, Object> outputFields = new HashMap<>();
	
	public TransactionInput getTransactionInput() {
		return transactionInput;
	}
	public void setTransactionInput(TransactionInput transactionInput) {
		this.transactionInput = transactionInput;
	}
	public TransactionStatus getStatus() {
		return status;
	}
	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
	public Map<String, Object> getOutputFields() {
		return outputFields;
	}
	public void setOutputFields(Map<String, Object> outputFields) {
		this.outputFields = outputFields;
	}
	
	
}
