package com.isslng.banking.processor.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MessageTemplate extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2461191559535554332L;

	@Id
	private String Id;
	
	private String type = "velocity";
	
	public MessageTemplate() {
		// TODO Auto-generated constructor stub
	}
	
	public MessageTemplate(String subject, String body) {
		super(subject,body);
		// TODO Auto-generated constructor stub
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
	
	
}
