package com.isslng.banking.processor.service;

import com.isslng.banking.processor.entities.TransactionInput;

public interface TransactionInputEvaluator {
	public TransactionInput evaluate(TransactionInput e);
}
