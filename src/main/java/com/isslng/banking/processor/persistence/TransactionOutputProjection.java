package com.isslng.banking.processor.persistence;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.isslng.banking.processor.entities.TransactionStatus;

public interface TransactionOutputProjection {
	@Value("#{target.transactionInput.id}")
	public String getTransactionId();
	public TransactionStatus getStatus();
	public Map<String,Object> getOutputFields();
}
