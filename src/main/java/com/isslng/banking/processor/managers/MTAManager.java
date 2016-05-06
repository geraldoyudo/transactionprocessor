package com.isslng.banking.processor.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.MTA;
import com.isslng.banking.processor.entities.MessageTemplate;
import com.isslng.banking.processor.entities.TransactionNotification;
import com.isslng.banking.processor.persistence.MTARepository;

@Component
public class MTAManager extends BasicRepositoryManager<MTARepository, MTA, String>{
	@Autowired
	private MessageTemplateManager mtManager;
	
	public MTA getByParameters(String orgCode, String transactionCode, 
			TransactionNotification notificationType){
		try{
			return managedRepository
					.findByOrgCodeAndTransactionCodeAndNotificationTypeAndChannel(orgCode
							, transactionCode, notificationType, null).get(0);
		}catch(IndexOutOfBoundsException ex){
			return null;
		}
	}
	public MTA getByParameters(String channel, String orgCode, String transactionCode, 
			TransactionNotification notificationType){
		try{
			return managedRepository
					.findByOrgCodeAndTransactionCodeAndNotificationTypeAndChannel(orgCode
							, transactionCode, notificationType, channel).get(0);
		}catch(IndexOutOfBoundsException ex){
			try{
				return managedRepository
						.findByOrgCodeAndTransactionCodeAndNotificationTypeAndChannel(orgCode
								, transactionCode, notificationType, null).get(0);
			}catch(IndexOutOfBoundsException e){
				return null;
			}
			
		}
	}
	
	public MTA getByParameters(String channel, String orgCode, String transactionCode, 
			TransactionNotification notificationType, boolean getChannelDefault){
		try{
			return managedRepository
					.findByOrgCodeAndTransactionCodeAndNotificationTypeAndChannel(orgCode
							, transactionCode, notificationType, channel).get(0);
		}catch(IndexOutOfBoundsException ex){
			if(getChannelDefault){
				try{
					return managedRepository
							.findByOrgCodeAndTransactionCodeAndNotificationTypeAndChannel(orgCode
									, transactionCode, notificationType, null).get(0);
				}catch(IndexOutOfBoundsException e){
					return null;
				}
			}else{
				return null;
			}
			
		}
	}
	
	
	public MTA registerTemplate (MessageTemplate mt, String orgCode, 
			String transactionCode, 
			TransactionNotification notificationType){
		mt = mtManager.save(mt);
		MTA mta = getByParameters( orgCode, transactionCode, notificationType);
		if(mta == null){
			mta = new MTA();
			mta.setOrgCode(orgCode);
			mta.setTransactionCode(transactionCode);
			mta.setNotificationType(notificationType);
			mta.setChannel(null);
		}
		mta.setMessageTemplate(mt);
		return save(mta);
	}
	public MTA registerTemplate (MessageTemplate mt, String channel, String orgCode, 
			String transactionCode, 
			TransactionNotification notificationType){
		mt = mtManager.save(mt);
		MTA mta = getByParameters( channel, orgCode, transactionCode, notificationType, false);
		if(mta == null){
			mta = new MTA();
			mta.setOrgCode(orgCode);
			mta.setTransactionCode(transactionCode);
			mta.setNotificationType(notificationType);
			mta.setChannel(channel);
		}
		mta.setMessageTemplate(mt);
		return save(mta);
	}
	
	public MessageTemplate getMessageTemplate( String orgCode, String transactionCode, 
			TransactionNotification notificationType){
		// check for exact template for configuration
		MTA mta = getByParameters( orgCode, transactionCode, notificationType);
		if(mta != null)
			return mta.getMessageTemplate();
		if(orgCode != null){
			// check default pertaining to org code
			mta = getByParameters(orgCode, null, notificationType);
			if(mta != null)
				return mta.getMessageTemplate();
			// check default pertaining to transaction code
			mta = getByParameters(null , transactionCode, notificationType);
			if(mta != null)
				return mta.getMessageTemplate();
			// check global defaults
			mta = getByParameters(null , null, notificationType);
			if(mta != null)
				return mta.getMessageTemplate();
		}
		return null;
	}
	public MessageTemplate getMessageTemplate( String channel, String orgCode, String transactionCode, 
			TransactionNotification notificationType){
		// check for exact template for configuration
		MTA mta = getByParameters( channel, orgCode, transactionCode, notificationType);
		if(mta != null)
			return mta.getMessageTemplate();
		if(orgCode != null){
			// check default pertaining to org code
			mta = getByParameters(channel, orgCode, null, notificationType);
			if(mta != null)
				return mta.getMessageTemplate();
			// check default pertaining to transaction code
			mta = getByParameters(channel, null , transactionCode, notificationType);
			if(mta != null)
				return mta.getMessageTemplate();
			// check global defaults
			mta = getByParameters(channel, null , null, notificationType);
			if(mta != null)
				return mta.getMessageTemplate();
		}
		return null;
	}
}
