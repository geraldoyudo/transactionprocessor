package com.isslng.banking.processor.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface TransactionInputRepository extends MongoRepository<TransactionInput, String> {

}
