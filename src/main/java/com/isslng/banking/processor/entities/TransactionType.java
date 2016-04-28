package com.isslng.banking.processor.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TransactionType extends NotifyableResource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Indexed(unique = true)
	private String code;
	@NotNull
	@DBRef
	private Processor primaryProcessor;
	private Map<String,Boolean> inputFields = new HashMap<>();
	private Map<String, Boolean> outputFields = new HashMap<>();

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Processor getPrimaryProcessor() {
		return primaryProcessor;
	}
	public void setPrimaryProcessor(Processor primaryProcessor) {
		this.primaryProcessor = primaryProcessor;
	}
	
	public Map<String, Boolean> getInputFields() {
		return inputFields;
	}
	public void setInputFields(Map<String, Boolean> inputFields) {
		this.inputFields = inputFields;
	}
	public Map<String, Boolean> getOutputFields() {
		return outputFields;
	}
	public void setOutputFields(Map<String, Boolean> outputFields) {
		this.outputFields = outputFields;
	}
	
	
}
