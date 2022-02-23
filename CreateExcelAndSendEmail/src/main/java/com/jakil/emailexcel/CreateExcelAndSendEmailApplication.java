package com.jakil.emailexcel;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.jakil.emailexcel.emailutil.EmailSender;
import com.jakil.emailexcel.service.ExcelService;

@SpringBootApplication
public class CreateExcelAndSendEmailApplication {

	@Autowired
	private ExcelService service;

	public static void main(String[] args) {
		SpringApplication.run(CreateExcelAndSendEmailApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void triggerMail() throws MessagingException {

		service.processExcelAndEmail();
		System.out.println("Email sent Successfully. Message from triggerMail() method of main class");
	}

}
