package com.isslng.banking.processor.entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;

public abstract class NotifyableResource extends Resource{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DBRef
	private Set<Processor> approvalNotificationProcessors = new HashSet<>();
	@DBRef
	private Set<Processor> rejectionNotificationProcessors = new HashSet<>();
	@DBRef
	private Set<Processor> completionNotificationProcessors = new HashSet<>();
	
	public Set<Processor> getApprovalNotificationProcessors() {
		return approvalNotificationProcessors;
	}
	public void setApprovalNotificationProcessors(Set<Processor> approvalNotificationProcessors) {
		this.approvalNotificationProcessors = approvalNotificationProcessors;
	}
	public Set<Processor> getRejectionNotificationProcessors() {
		return rejectionNotificationProcessors;
	}
	public void setRejectionNotificationProcessors(Set<Processor> rejectionNotificationProcessors) {
		this.rejectionNotificationProcessors = rejectionNotificationProcessors;
	}
	public Set<Processor> getCompletionNotificationProcessors() {
		return completionNotificationProcessors;
	}
	public void setCompletionNotificationProcessors(Set<Processor> completionNotificationProcessors) {
		this.completionNotificationProcessors = completionNotificationProcessors;
	}
	
	
	
}
