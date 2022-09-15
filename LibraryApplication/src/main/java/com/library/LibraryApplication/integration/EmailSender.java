package com.library.LibraryApplication.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    Logger logger= LoggerFactory.getLogger(EmailSender.class);

    public void sendEmail(String email, String message){
        logger.info("Sending message to customer "+ email + ", message["+message+"]");
    }
}
