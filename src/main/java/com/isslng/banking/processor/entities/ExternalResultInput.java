package com.isslng.banking.processor.entities;

import java.util.Map;

public class ExternalResultInput {
	private String id;
	private String processor;
	private Map<String, Object> output;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public Map<String, Object> getOutput() {
		return output;
	}
	public void setOutput(Map<String, Object> output) {
		this.output = output;
	}
	
	
}
