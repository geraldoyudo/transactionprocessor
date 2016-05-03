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
	@Value("${app.drools.rules}")
	private String rulesFolder;
	
	@PostConstruct
	public void moveFiles(){
		
		if(rulesFolder != null){
			try {
			File source = new File(rulesFolder);
			URL url = this.getClass().getResource("/rules");
			
			File dest = new File(url.toURI());
			
			    FileUtils.copyDirectory(source, dest);
			    System.out.println( String.format("Files for %s successfully loaded",rulesFolder));
			} catch (IOException e) {
			    e.printStackTrace();
			} catch (Exception e){
				 e.printStackTrace();
			}
		}else{
			System.out.println(String.format("Files at %s not loaded",rulesFolder));
		}
		
		
	}
}
