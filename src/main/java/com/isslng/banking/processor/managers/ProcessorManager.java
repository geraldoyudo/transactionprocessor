package com.isslng.banking.processor.managers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.isslng.banking.processor.persistence.Processor;
import com.isslng.banking.processor.persistence.ProcessorRepository;

@Component
public class ProcessorManager extends BasicRepositoryManager
<ProcessorRepository, Processor, String>{
	
	public Processor getByName(String name){
		try{
			return managedRepository.findByName(name).get(0);
		}catch(IndexOutOfBoundsException ex){
			return null;
		}
	}
	
	public Set<String> toProcessorUrl(Set<Processor> processors){
		Set<String> processorStr = new HashSet<>();
		for(Processor p: processors){
			processorStr.add(p.getUrl());
		}
		return processorStr;
	}
}
