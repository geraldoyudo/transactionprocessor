package com.isslng.banking.processor;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TransactionNotificationRoutes extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("jms:notifications")
			.log("${headers.notifyType} ${body}")
			.recipientList().spel("#{@notificationSlipManager.getNotificationSlips"
					+ "(body,request.headers['notifyType'])}").ignoreInvalidEndpoints();
		from("jms:user-notification")
		.log("USER ${headers.notifyType} ${body}");
		from("jms:type-notification")
		.log("TYPE ${headers.notifyType} ${body}");
		from("jms:tenant-notification")
		.log("TENANT ${headers.notifyType} ${body}");
		
	}

}
