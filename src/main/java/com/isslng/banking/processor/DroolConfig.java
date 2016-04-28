package com.isslng.banking.processor;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.isslng.banking.processor.service.DroolsImporter;

@Configuration
public class DroolConfig {
	@Autowired
	DroolsImporter droolsImporter;
	@Bean
	KieServices kieServices(){
		return KieServices.Factory.get();
	}
	@Bean
	KieContainer kieContainer(KieServices kieServices){
		return kieServices.getKieClasspathContainer();
	}
	
	@Bean 
	KieSession approvalEvaluatorSession(KieContainer kieContainer){
		return kieContainer.newKieSession("approval-evaluation");
	}
}
