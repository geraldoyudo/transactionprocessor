package com.isslng.banking.processor.entities;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndex(name = "msgTemp_org_trans_notify", def = "{'messageTemplate.$id' : 1, "
		+ "'orgCode' : 1, 'transactionCode' : 1, 'notificationType' : 1}")
public class MTA extends Resource{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBRef
	private MessageTemplate messageTemplate;
	private String orgCode;
	@NotNull
	private TransactionNotification notificationType;
	private String transactionCode;
	public MTA() {
		// TODO Auto-generated constructor stub
	}
	
	public MTA(String orgCode, String transactionCode, TransactionNotification notificationType) {
		// TODO Auto-generated constructor stub
		this.orgCode = orgCode;
		this.transactionCode = transactionCode;
		this.notificationType = notificationType;
	}
	
	public MessageTemplate getMessageTemplate() {
		return messageTemplate;
	}
	public void setMessageTemplate(MessageTemplate messageTemplate) {
		this.messageTemplate = messageTemplate;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public TransactionNotification getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(TransactionNotification notificationType) {
		this.notificationType = notificationType;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	
	
	
}
