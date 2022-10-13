package com.masterswork.mail;

import com.masterswork.mail.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;

@SpringBootApplication
@RequiredArgsConstructor
@ConfigurationPropertiesScan("com.masterswork.mail.config")
public class MailApplication {

	private final EmailSenderService senderService;

	public static void main(String[] args) {
		SpringApplication.run(MailApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void triggerMail() throws MessagingException {
		senderService.sendSimpleEmail("to@gmail.com",
				"This is email body test",
				"This is email subject");

	}
}
