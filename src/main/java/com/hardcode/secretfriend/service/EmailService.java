package com.hardcode.secretfriend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.hardcode.secretfriend.model.Email;


@Service
public class EmailService {
	
	@Autowired
	private MailSender sender;

	public void sendEmail(Email email) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setSubject("teste da api");
		message.setTo(email.getTo());
		message.setText(email.getContent());
		message.setFrom("hardcode.comercial@gmail.com");
		
		sender.send(message);
		
	}

}
