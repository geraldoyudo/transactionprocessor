package com.isslng.banking.processor.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.isslng.banking.processor.entities.TransactionInput;


public interface TransactionInputRepository extends MongoRepository<TransactionInput, String> {

}
