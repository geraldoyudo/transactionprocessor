package com.isslng.banking.processor.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.isslng.banking.processor.entities.Organization;

public interface OrganizationRepository extends MongoRepository<Organization, String>{
	public List<Organization> findByCode(String code);
}
