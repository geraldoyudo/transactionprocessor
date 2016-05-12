package com.isslng.banking.processor.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndex(name = "org_ref", def = "{'orgCode': 1, 'ref': 1}")
public class TransactionInput extends  Resource implements TransactionReference{
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
	@NotNull
	private String orgCode;
	private Map<String,Object> transactionFields = new HashMap<>();
	private Map<String,Object> userDetails = new HashMap<>();
	private List<String> outputRefs = new ArrayList<>();
	private long ref;
	
	private boolean needsApproval = false;
	private boolean approved = true;
	public boolean approvalRejected = false;
	
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
	public boolean getNeedsApproval() {
		return needsApproval;
	}
	public void setNeedsApproval(boolean needsApproval) {
		this.needsApproval = needsApproval;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public boolean isApprovalRejected() {
		return approvalRejected;
	}
	public void setApprovalRejected(boolean approvalRejected) {
		this.approvalRejected = approvalRejected;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Map<String, Object> getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(Map<String, Object> userDetails) {
		this.userDetails = userDetails;
	}
	public long getRef() {
		return ref;
	}
	public void setRef(long ref) {
		this.ref = ref;
	}
	public List<String> getOutputRefs() {
		return outputRefs;
	}
	public void setOutputRefs(List<String> outputRefs) {
		this.outputRefs = outputRefs;
	}
	
	
	
	
}
