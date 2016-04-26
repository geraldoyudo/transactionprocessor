package com.isslng.banking.processor.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.isslng.banking.processor.entities.TransactionType;

@RepositoryRestResource
public interface TransactionTypeRepository extends MongoRepository<TransactionType, String> {
	public List<TransactionType> findByCode(String code);
}
