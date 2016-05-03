package com.isslng.banking.processor.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.isslng.banking.processor.entities.TransactionOutput;


public interface TransactionOutputRepository extends MongoRepository<TransactionOutput, String>{

}
