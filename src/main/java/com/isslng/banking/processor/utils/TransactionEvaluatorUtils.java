package com.isslng.banking.processor.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.isslng.banking.processor.entities.TransactionInput;
import com.isslng.banking.processor.entities.TransactionNotification;

public class TransactionEvaluatorUtils {
	public static void setNotificationLevels(TransactionInput ti,String notifyType, String ... levels){
		TransactionNotification type;
		try{
			 type = TransactionNotification.valueOf(notifyType);
		}catch(IllegalArgumentException ex){
			type = null;
		}
		List<String> userLevels = Arrays.asList(levels);
		if(notifyType != null && notifyType.equals("ALL")){
			String [] all = {"COMPLETED", "APPROVED", "REJECTED"};
			for(String phase: all){
				ti.meta(phase, userLevels);
			}
			return;
		}
		if(type != null){
			ti.meta(type.toString(), userLevels);
		}	
	}
	
	public static void setUserNotificationChannels(TransactionInput ti,String notifyType, String ... channelStrs){
		List<String> channels = Arrays.asList(channelStrs);
		TransactionNotification type;
		try{
			 type = TransactionNotification.valueOf(notifyType);
		}catch(IllegalArgumentException ex){
			type = null;
		}
		Map<String, List<String>> channelMap = new HashMap<>();
		Map<String, List<String>> existingMap = (Map<String,List<String>>)ti.meta("userChannels");
		if(existingMap != null){
			channelMap.putAll(existingMap);
		}
		if(notifyType != null && notifyType.equals("ALL")){
			String [] all = {"COMPLETED", "APPROVED", "REJECTED"};
			for(String phase: all){
				channelMap.put(phase, channels);
			}
		}else if(type != null){
			channelMap.put(type.toString(), channels);
		}	
		ti.meta("userChannels", channelMap);
	}
}
