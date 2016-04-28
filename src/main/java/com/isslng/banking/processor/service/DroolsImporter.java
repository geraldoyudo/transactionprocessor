package com.isslng.banking.processor.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DroolsImporter {
	@Value("${app.drools.folder.approval}")
	private String droolsApprovalFolder;
	
	@PostConstruct
	public void moveFiles() throws URISyntaxException{
		if(droolsApprovalFolder != null){
			File source = new File(droolsApprovalFolder);
			URL url = this.getClass().getResource("/approval");
			
			File dest = new File(url.toURI());
			try {
			    FileUtils.copyDirectory(source, dest);
			    System.out.println("Files successfully copied");
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}else{
			System.out.println("No drools folder specified");
		}
	}
}
