package com.isslng.banking.processor.service;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class SMSService extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("seda:sms")
		.to("bean:smsGenerator")
		.marshal().json(JsonLibrary.Jackson)
		.log("Details ${headers} ${body}")
		.to("https4:api.infobip.com/sms/1/text/single");
	}

}
