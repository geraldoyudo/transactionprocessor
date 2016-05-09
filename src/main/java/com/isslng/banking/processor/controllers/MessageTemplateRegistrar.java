package com.isslng.banking.processor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isslng.banking.processor.entities.MTA;
import com.isslng.banking.processor.managers.MTAManager;

@RestController
public class MessageTemplateRegistrar {
	@Autowired
	private MTAManager mtaManager;
	@RequestMapping(path = "/registerTemplate", method = RequestMethod.POST)
	public MTA register(@RequestBody MTA mta){
		System.out.println(mta.getMessageTemplate());
		return mtaManager.registerTemplate(mta.getMessageTemplate(), 
				mta.getChannel(),mta.getOrgCode(), mta.getTransactionCode(), 
				mta.getNotificationType());
	}
}
