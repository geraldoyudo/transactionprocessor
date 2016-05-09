package com.isslng.banking.processor.entities;

public class SMSMessage {
	private String from;
	private String to;
	private String text;
	
	public SMSMessage(String to, String from, String body) {
		this.from = from;
		this.to= to;
		text = body;
	}
	public SMSMessage(){
		
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
