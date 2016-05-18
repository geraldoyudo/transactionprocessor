package com.isslng.banking.processor.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name= "exception", types = Exception.class)
public interface ExceptionProjection {
	public String getLocalizedMessage();
	public String getMessage();
}
