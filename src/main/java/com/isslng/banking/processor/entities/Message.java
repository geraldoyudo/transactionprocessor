package com.isslng.banking.processor.entities;

import java.io.Serializable;

public class Message implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2814429955062180187L;
	private String subject;
	private String body;
	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	public Message(String subject, String body) {
		this.subject = subject;
		this.body = body;
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
