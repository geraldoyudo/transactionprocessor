package com.isslng.banking.processor.entities;

import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TransactionInput extends  Resource{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3049199770677754810L;
	
	@NotNull
	private String code;
	@NotNull
	private String ipAddress;
	@NotNull
	private String user;
	@NotNull
	private Date date;
	private Map<String,Object> transactionFields;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Map<String, Object> getTransactionFields() {
		return transactionFields;
	}
	public void setTransactionFields(Map<String, Object> transactionFields) {
		this.transactionFields = transactionFields;
	}
	
	
}
