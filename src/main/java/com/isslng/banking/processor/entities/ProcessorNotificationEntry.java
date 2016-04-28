package com.isslng.banking.processor.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ProcessorNotificationEntry {
	@Id
	public String Id;
	private TransactionNotification notificationType;
	@DBRef
	private Processor processor;
	
	public ProcessorNotificationEntry() {
		// TODO Auto-generated constructor stub
	}
	public ProcessorNotificationEntry(Processor p, TransactionNotification t){
		processor = p;
		notificationType = t;
	}
	public TransactionNotification getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(TransactionNotification notificationType) {
		this.notificationType = notificationType;
	}
	public Processor getProcessor() {
		return processor;
	}
	public void setProcessor(Processor processor) {
		this.processor = processor;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processor == null) ? 0 : processor.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessorNotificationEntry other = (ProcessorNotificationEntry) obj;
		if (processor == null) {
			if (other.processor != null)
				return false;
		} else if (!processor.equals(other.processor))
			return false;
		return true;
	}
	
	
}
