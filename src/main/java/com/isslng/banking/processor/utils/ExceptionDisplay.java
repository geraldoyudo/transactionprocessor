package com.isslng.banking.processor.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.ExceptionProjection;

@Component
public class ExceptionDisplay {
	@Autowired
	private ProjectionFactory projectionFactory;
	
	public ExceptionProjection process(Exception ex){
		return projectionFactory.createProjection(ExceptionProjection.class, ex);
	}
}
