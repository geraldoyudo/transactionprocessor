package com.isslng.banking.processor.persistence;

import java.util.Date;
import java.util.Map;

public class TransactionInput {
	private String id;
	private String code;
	private String ipAddress;
	private String user;
	private Date date;
	private Map<String,Object> transactionFields;
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
