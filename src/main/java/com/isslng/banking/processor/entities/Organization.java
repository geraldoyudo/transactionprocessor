package com.isslng.banking.processor.entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Organization extends NotifyableResource{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Indexed(unique = true)
	private String code;
	@Indexed(unique = true)
	private String name;
	private String description;
	private Set<UserChannel> userChannels = new HashSet<>();
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<UserChannel> getUserChannels() {
		return userChannels;
	}
	public void setUserChannels(Set<UserChannel> userChannels) {
		this.userChannels = userChannels;
	}
	public UserChannel getChannelByName(String name){
		for(UserChannel uc: userChannels){
			if(uc.getName().equals("name"))
				return uc;
		}
		return null;
	}
	public Set<UserChannel> getChannelsByService(String serviceName){
		Set<UserChannel> channels = new HashSet<>();
		for(UserChannel uc:userChannels){
			if(uc.getNotificationService().equals(serviceName)){
				channels.add(uc);
			}
		}
		return channels;
	}
	
}
