package com.example.L21_email_sender_circuit_breaker_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private static Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailRequest emailRequest)
    {


        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("sk.email.service@gmail.com");
        simpleMailMessage.setSubject(emailRequest.getEmailSubject());
        simpleMailMessage.setTo(emailRequest.getEmailTo());
        simpleMailMessage.setText(emailRequest.getEmailBody());
        simpleMailMessage.setCc(emailRequest.getEmailCC());
        javaMailSender.send(simpleMailMessage);
        LOGGER.info("Email sent to: {}",emailRequest.getEmailTo());
        return "Email sent to: "+emailRequest.getEmailTo();
    }
}
