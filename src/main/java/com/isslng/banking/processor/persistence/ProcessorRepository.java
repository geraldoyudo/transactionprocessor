package com.isslng.banking.processor.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.isslng.banking.processor.entities.Processor;

import java.lang.String;
import java.util.List;

public interface ProcessorRepository extends MongoRepository<Processor, String>{
	List<Processor> findByName(String name);
}
