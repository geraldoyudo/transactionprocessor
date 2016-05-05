package com.isslng.banking.processor.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.isslng.banking.processor.entities.MTA;
import com.isslng.banking.processor.entities.TransactionNotification;

public interface MTARepository extends MongoRepository<MTA, String>{
	List<MTA> findByOrgCodeAndTransactionCodeAndNotificationType(String orgCode
			, String transactionCode, TransactionNotification notificationType);
}
