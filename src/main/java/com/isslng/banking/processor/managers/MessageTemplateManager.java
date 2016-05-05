package com.isslng.banking.processor.managers;

import org.springframework.stereotype.Component;

import com.isslng.banking.processor.entities.MessageTemplate;
import com.isslng.banking.processor.persistence.MessageTemplateRepository;

@Component
public class MessageTemplateManager extends BasicRepositoryManager
<MessageTemplateRepository, MessageTemplate, String>{

}
