package com.isslng.banking.processor.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.lang.String;
import com.isslng.banking.processor.persistence.Processor;
import java.util.List;

public interface ProcessorRepository extends MongoRepository<Processor, String>{
	List<Processor> findByName(String name);
}
