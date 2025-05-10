package com.cambyze.home.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

  @Autowired
  private JavaMailSender mailSender;

  @PostMapping("/contact")
  public String handleContact(@RequestParam("name") String name,
      @RequestParam("email") String email, @RequestParam("message") String message) {
    System.out.println("Received contact form from " + name + " (" + email + "): " + message);

    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo("support@cambyze.com");
    mail.setSubject("New Contact Form Submission");
    mail.setText("From: " + name + " <" + email + ">\n\n" + message);
    mail.setFrom("support@cambyze.com");
    try {
      mailSender.send(mail);
      System.out.println("Email sent successfully");
    } catch (Exception e) {
      System.err.println("Failed to send email: " + e.getMessage());
      e.printStackTrace();
    }

    return "Message sent";
  }
}
