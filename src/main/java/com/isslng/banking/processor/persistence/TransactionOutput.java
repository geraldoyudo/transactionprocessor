package com.isslng.banking.processor.persistence;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.isslng.banking.processor.entities.TransactionStatus;

@Document
public class TransactionOutput implements Serializable {
	@Id
	private String id;
	@DBRef
	private TransactionInput transactionInput;
	private TransactionStatus status;
	private Map<String, Object> outputFields = new HashMap<>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
