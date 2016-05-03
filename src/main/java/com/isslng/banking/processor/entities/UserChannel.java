package com.isslng.banking.processor.entities;

import java.util.HashMap;
import java.util.Map;

public class UserChannel {
	private String name;
	private String notificationService;
	private Map<String,Object> properties = new HashMap<>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNotificationService() {
		return notificationService;
	}
	
	public void setNotificationService(String notificationService) {
		this.notificationService = notificationService;
	}
	
	public Map<String, Object> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	public Object getProperty(String prop){
		return properties.get(prop);
	}
	public Object setProperty(String prop, Object val){
		return properties.put(prop, val);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserChannel other = (UserChannel) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}

