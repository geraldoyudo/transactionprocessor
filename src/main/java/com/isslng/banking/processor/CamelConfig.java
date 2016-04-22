package com.isslng.banking.processor;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class CamelConfig {
	private static final String CAMEL_URL_MAPPING = "/tps/*";
	private static final String CAMEL_SERVLET_NAME = "CamelServlet";
	
	
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), CAMEL_URL_MAPPING);
        registration.setName(CAMEL_SERVLET_NAME);
        return registration;
    }
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                dispatcherServlet);
        
        registration.addUrlMappings("/app/*");
        return registration;
    }
}
