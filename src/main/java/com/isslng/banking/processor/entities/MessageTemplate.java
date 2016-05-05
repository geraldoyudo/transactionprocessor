package com.isslng.banking.processor.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MessageTemplate extends Resource {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2461191559535554332L;

	
	private String subject;
	private String body;
	
	private String type = "velocity";
	
	public MessageTemplate() {
		// TODO Auto-generated constructor stub
	}
	
	public MessageTemplate(String subject, String body) {
		this.subject = subject;
		this.body = body;
		// TODO Auto-generated constructor stub
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
