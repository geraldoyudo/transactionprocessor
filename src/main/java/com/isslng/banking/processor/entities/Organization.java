package com.isslng.banking.processor.entities;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Organization extends NotifyableResource{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Indexed(unique = true)
	private String code;
	@Indexed(unique = true)
	private String name;
	private String description;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
