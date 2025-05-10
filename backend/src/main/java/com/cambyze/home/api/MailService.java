// src/main/java/com/cambyze/api/MailService.java
package com.cambyze.home.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

  @Autowired
  private JavaMailSender mailSender;

  public void sendSupportMail(String fromName, String fromEmail, String messageBody) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo("support@cambyze.com");
    message.setSubject("New message from Cambyze contact form");
    message.setText("From: " + fromName + " <" + fromEmail + ">\n\n" + messageBody);

    mailSender.send(message);
  }
}
