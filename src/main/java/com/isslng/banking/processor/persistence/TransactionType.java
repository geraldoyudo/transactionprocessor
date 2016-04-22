package com.isslng.banking.processor.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TransactionType {
	@Id
	private String id;
	@Indexed(unique = true)
	private String code;
	@NotNull
	private String primaryProcessor;
	private Set<String> secondaryProcessor;
	private Map<String,Boolean> inputFields = new HashMap<>();
	private Map<String, Boolean> outputFields = new HashMap<>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPrimaryProcessor() {
		return primaryProcessor;
	}
	public void setPrimaryProcessor(String primaryProcessor) {
		this.primaryProcessor = primaryProcessor;
	}
	public Set<String> getSecondaryProcessors() {
		return secondaryProcessor;
	}
	public void setSecondaryProcessor(Set<String> secondaryProcessor) {
		this.secondaryProcessor = secondaryProcessor;
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
