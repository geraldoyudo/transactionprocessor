package com.isslng.banking.processor.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.isslng.banking.processor.entities.MessageTemplate;

public interface MessageTemplateRepository 
extends MongoRepository<MessageTemplate, String>{
	 
}
