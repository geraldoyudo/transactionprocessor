package com.isslng.banking.processor.managers;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

public abstract class BasicRepositoryManager <T extends MongoRepository<V,W>, V, W extends Serializable> {
	@Autowired
	protected T managedRepository;
	
	public V get(W id){
		return managedRepository.findOne(id);
	}
	
	public V save(V managedObject){
		return managedRepository.save(managedObject);
	}
	
}
