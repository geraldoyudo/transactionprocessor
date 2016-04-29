package com.isslng.banking.processor.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionOutput;
import com.isslng.banking.processor.entities.TransactionUtils.Level;

@Component
public class NotificationSlipManager {
	public ArrayList<String> getNotificationSlips(TransactionInput ti, String notifyType){
		System.out.println(notifyType);
		ArrayList<String> destinations = new ArrayList<>();
		Object list = ti.meta(notifyType);
		if(list == null){
			return destinations;
		}
		String url;
		for(String level: (Collection<String>)list){
			url = getUrl(level);
			if(url != null && !url.isEmpty())
				destinations.add(url);
		}
		System.out.println(Arrays.toString(destinations.toArray(new String[]{})));
		return destinations;
	}
	
	public ArrayList<String> getNotificationSlips(TransactionOutput to, String notifyType){
		return getNotificationSlips(to.getTransactionInput(), notifyType);
	}
	private String getUrl(String notifyLevel){
		Level tn = Level.valueOf(notifyLevel);
		switch(tn){
		case USER:{
			return "jms:user-notification";
		}
		case TYPE:{
			return "jms:type-notification";
		}
		case TENANT:{
			return "jms:tenant-notification";
		}
		default: return "";
		}
	}
}
