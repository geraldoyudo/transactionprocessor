package com.isslng.banking.processor.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;

public abstract class Resource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String Id;
	private Map<String,Object> meta = new HashMap<>();
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public Map<String, Object> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
	
	
}
