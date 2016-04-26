package com.isslng.banking.processor.entities;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Processor extends Resource{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Indexed(unique = true)
	private String name;
	@Indexed(unique = true)
	private String url;
	private String description;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
