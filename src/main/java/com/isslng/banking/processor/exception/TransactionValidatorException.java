package com.isslng.banking.processor.exception;

public class TransactionValidatorException extends RuntimeException{
	public TransactionValidatorException() {
		super();
	}
	public TransactionValidatorException(String message){
		super(message);
	}
	public TransactionValidatorException(Throwable t){
		super(t);
	}
	public TransactionValidatorException(String message, Throwable t){
		super(message,t);
	}
	
}
