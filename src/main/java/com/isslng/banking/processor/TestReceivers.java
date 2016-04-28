package com.isslng.banking.processor;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TestReceivers  extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("jms:topic:processor-COMPLETED")
		.log("${body}");
		from("jms:topic:processor-APPROVED")
		.log("${body}");
		from("jms:topic:processor-REJECTED")
		.log("${body}");
	}

}
