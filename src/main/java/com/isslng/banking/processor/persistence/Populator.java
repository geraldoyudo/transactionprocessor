package com.isslng.banking.processor.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.isslng.banking.processor.entities.Organization;
import com.isslng.banking.processor.entities.Processor;
import com.isslng.banking.processor.entities.TransactionNotification;
import com.isslng.banking.processor.entities.TransactionType;

@Component
public class Populator {
	@Autowired
	TransactionTypeRepository ttRepository;
	@Autowired
	ProcessorRepository pRepository;
	@Autowired
	OrganizationRepository orgRepository;
	
	@PostConstruct
	public void initRepo(){
		if(ttRepository.findAll().isEmpty()){
			Organization org = new Organization();
			org.setCode("ISSL");
			org.setName("ISSLNG");
			org.setDescription("Test organization");
			org = orgRepository.save(org);
			
			Processor pCash;
			pCash = new Processor();
			pCash.setName("general-processor");
			pCash.setUrl("jms:cash-receipt");
			pCash.setDescription("This endpoint processes cash receipt for general use");
			pRepository.save(pCash);
			
			Processor p1 = new Processor();
			p1.setName("completion-processor");
			p1.setUrl("jms:topic:processor-COMPLETED");
			p1.setDescription("This tests secondary processing");
            p1 = pRepository.save(p1);
            
            Processor p2 = new Processor();
			p2.setName("approval-processor");
			p2.setUrl("jms:topic:processor-APPROVED");
			p2.setDescription("This tests secondary processing");
		    p2 = pRepository.save(p2);
		    
		    Processor p3 = new Processor();
			p3.setName("rejection-processor");
			p3.setUrl("jms:topic:processor-REJECTED");
			p3.setDescription("This tests secondary processing");
		    p3 = pRepository.save(p3);
			
		    org.setCompletionNotificationProcessors(Sets.newHashSet(p1));
		    org.setApprovalNotificationProcessors(Sets.newHashSet(p2));
		    org.setRejectionNotificationProcessors(Sets.newHashSet(p3));
			org = orgRepository.save(org);
		   
			TransactionType t = new TransactionType();
			t.setCode("TR-CASH-RECEIPT");
			t.setPrimaryProcessor(pCash);
			Map<String,Boolean> outputFields= new HashMap<>();
			outputFields.put("message", true);
			Map<String,Boolean> inputFields= new HashMap<>();
			inputFields.put("cashier", true);
			inputFields.put("cashierAccount", true);
			inputFields.put("amount", true);
			inputFields.put("narrative", false);
			inputFields.put("contraAccount", true);
			inputFields.put("contraAccountName", true);
			t.setInputFields(inputFields);
			t.setOutputFields(outputFields);
			ttRepository.save(t);
			
		
			t = new TransactionType();
			t.setCode("TR-CASH-PAYMENT");
			t.setPrimaryProcessor(pCash);
			outputFields= new HashMap<>();
			outputFields.put("message", true);
		    inputFields= new HashMap<>();
		    inputFields.put("cashier", true);
			inputFields.put("cashierAccount", true);
			inputFields.put("amount", true);
			inputFields.put("narrative", false);
			inputFields.put("contraAccount", true);
			inputFields.put("contraAccountName", true);
			t.setInputFields(inputFields);
			t.setOutputFields(outputFields);
			ttRepository.save(t);
			
		
			t = new TransactionType();
			t.setCode("TR-CHEQUE-RECEIPT");
			t.setPrimaryProcessor(pCash);
			outputFields= new HashMap<>();
			outputFields.put("message", true);
		    inputFields= new HashMap<>();
		    inputFields.put("bank", true);
			inputFields.put("cashierAccount", true);
			inputFields.put("amount", true);
			inputFields.put("narrative", false);
			inputFields.put("contraAccount", true);
			inputFields.put("contraAccountName", true);
			t.setInputFields(inputFields);
			t.setOutputFields(outputFields);
			ttRepository.save(t);
			
			//DraweeName,DraweeAccount,ChequeNo,ChequeDate,Amount,
			//LodgementBank,LodgementBankAccount,ContraAccount,ContraAccountName,Narrative
			t = new TransactionType();
			t.setCode("TR-CHEQUE-LODGEMENT");
			t.setPrimaryProcessor(pCash);
			outputFields= new HashMap<>();
			outputFields.put("message", true);
		    inputFields= new HashMap<>();
		    inputFields.put("draweeName", true);
			inputFields.put("draweeAccount", true);
			inputFields.put("chequeNo", true);
			inputFields.put("chequeDate", false);
			inputFields.put("amount", true);
			inputFields.put("lodgementBank", true);
			inputFields.put("lodgementBankAccount", true);
			inputFields.put("contraAccount", true);
			inputFields.put("contraAccountName", true);
			inputFields.put("narrative", false);
			t.setInputFields(inputFields);
			t.setOutputFields(outputFields);
			ttRepository.save(t);
			
			//SourceBank,SourceBankAccount, ChequeNo,Amount, ChequeDate, BeneficiaryName, 
			//ContraAccount,ContraAccountName,Narrative
			t = new TransactionType();
			t.setCode("TR-CHEQUE-PAYMENT");
			t.setPrimaryProcessor(pCash);
			outputFields= new HashMap<>();
			outputFields.put("message", true);
		    inputFields= new HashMap<>();
		    inputFields.put("sourceBank", true);
			inputFields.put("sourceBankAccount", true);
			inputFields.put("chequeNo", true);
			inputFields.put("chequeDate", false);
			inputFields.put("amount", true);
			inputFields.put("beneficiaryName", true);
			inputFields.put("contraAccount", true);
			inputFields.put("contraAccountName", true);
			inputFields.put("narrative", false);
			t.setInputFields(inputFields);
			t.setOutputFields(outputFields);
			ttRepository.save(t);
			
				
			//SourceBank,SourceAccount,ChequeNo,Amount,
			//Payee,Narrative,PettyCashAccount
			t = new TransactionType();
			t.setCode("TR-PETTYCASH-FLOAT");
			t.setPrimaryProcessor(pCash);
			outputFields= new HashMap<>();
			outputFields.put("message", true);
		    inputFields= new HashMap<>();
		    inputFields.put("sourceBank", true);
			inputFields.put("sourceAccount", true);
			inputFields.put("chequeNo", true);
			inputFields.put("amount", true);
			inputFields.put("payee", true);
			inputFields.put("pettyCashAccount", true);
			inputFields.put("narrative", false);
			t.setInputFields(inputFields);
			t.setOutputFields(outputFields);
			ttRepository.save(t);
			
			
			//PettyCashAccount,Payee,Amount,Narrative
			t = new TransactionType();
			t.setCode("TR-PETTYCASH-PAYMENT");
			t.setPrimaryProcessor(pCash);
			outputFields= new HashMap<>();
			outputFields.put("message", true);
		    inputFields= new HashMap<>();
			inputFields.put("amount", true);
			inputFields.put("payee", true);
			inputFields.put("pettyCashAccount", true);
			inputFields.put("narrative", false);
			t.setInputFields(inputFields);
			t.setOutputFields(outputFields);
			ttRepository.save(t);

			//DocumentType,DocumentReference,DocumentDate,Remarks
			t = new TransactionType();
			t.setCode("TR-JOURNAL-VOUCHER");
			t.setPrimaryProcessor(pCash);
			outputFields= new HashMap<>();
			outputFields.put("message", true);
		    inputFields= new HashMap<>();
			inputFields.put("documentType", true);
			inputFields.put("documentReference", true);
			inputFields.put("documentDate", true);
			inputFields.put("remarks", false);
			t.setInputFields(inputFields);
			t.setOutputFields(outputFields);
			ttRepository.save(t);
			
			//ReceivingBank,ReceivingAccount,Amount,PupilId,
			//PupilName,Narrative,Reference
			t = new TransactionType();
			t.setCode("SCH-RECEIPT-TRANSFER");
			t.setPrimaryProcessor(pCash);
			outputFields= new HashMap<>();
			outputFields.put("message", true);
		    inputFields= new HashMap<>();
			inputFields.put("receivingBank", true);
			inputFields.put("receivingAccount", true);
			inputFields.put("amount", true);
			inputFields.put("pupilId", false);
			inputFields.put("pupilName", true);
			inputFields.put("narrative", false);
			inputFields.put("reference", true);
			t.setInputFields(inputFields);
			t.setOutputFields(outputFields);
			ttRepository.save(t);
			
			//ReceivingBank,ReceivingAccount,TellerNo,Amount,
			//PupilId,PupilName,Narrative,Reference
			t = new TransactionType();
			t.setCode("SCH-RECEIPT-BANK");
			t.setPrimaryProcessor(pCash);
			outputFields= new HashMap<>();
			outputFields.put("message", true);
		    inputFields= new HashMap<>();
			inputFields.put("receivingBank", true);
			inputFields.put("receivingAccount", true);
			inputFields.put("tellerNo", true);
			inputFields.put("amount", true);
			inputFields.put("pupilId", false);
			inputFields.put("pupilName", true);
			inputFields.put("narrative", false);
			inputFields.put("reference", true);
			t.setInputFields(inputFields);
			t.setOutputFields(outputFields);
			ttRepository.save(t);
			
			//Cashier,CashierAccount,Amount,PupilId
			//,PupilName,Narrative,Reference
			t = new TransactionType();
			t.setCode("SCH-RECEIPT-CASH");
			t.setPrimaryProcessor(pCash);
			outputFields= new HashMap<>();
			outputFields.put("message", true);
		    inputFields= new HashMap<>();
			inputFields.put("cashier", true);
			inputFields.put("cashierAccount", true);
			inputFields.put("amount", true);
			inputFields.put("pupilId", false);
			inputFields.put("pupilName", true);
			inputFields.put("narrative", false);
			inputFields.put("reference", true);
			t.setInputFields(inputFields);
			t.setOutputFields(outputFields);
			ttRepository.save(t);
			
			//Reference,SummaryDescription,
			//PupilId,PupilName,Amount,Narrative
			t = new TransactionType();
			t.setCode("SCH-RECEIPT-INVOICE");
			t.setPrimaryProcessor(pCash);
			outputFields= new HashMap<>();
			outputFields.put("message", true);
		    inputFields= new HashMap<>();
			inputFields.put("summaryDescription", false);
			inputFields.put("amount", true);
			inputFields.put("pupilId", false);
			inputFields.put("pupilName", true);
			inputFields.put("narrative", false);
			inputFields.put("reference", true);
			t.setInputFields(inputFields);
			t.setOutputFields(outputFields);
			ttRepository.save(t);


		}
		
	}
}
