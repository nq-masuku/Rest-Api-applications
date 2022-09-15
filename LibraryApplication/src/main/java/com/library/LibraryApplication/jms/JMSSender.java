package com.library.LibraryApplication.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.LibraryApplication.service.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class JMSSender implements IJMSSender{

	@Autowired
	JmsTemplate jmsTemplate;

	
	public void sendJMSMessage (WrapperObject book) throws JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		String bObj=mapper.writeValueAsString(book);
		System.out.println("JMSSender: sending JMS message ="+bObj);
		jmsTemplate.convertAndSend("BookApplicationQueue",bObj);
	}

}
