package com.isslng.banking.processor.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface TransactionOutputRepository extends MongoRepository<TransactionOutput, String>{

}
