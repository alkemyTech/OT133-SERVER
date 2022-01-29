package com.alkemy.ong.repository.service.impl;

import com.alkemy.ong.repository.service.MailService;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    Environment env;

    @Value("${emailSettings.sender}")
    private String emailSender;
    @Value("${emailSettings.enabled}")
    private boolean enabled;
//    @Value("$(emailSettings.apiKey)")
//    private String apiKey;

    public void sendTextEmail(String mailTo, String mailSubject, String mailContent) throws IOException{
        if (!enabled) {
            return;
        }

        String apiKey = env.getProperty("SENDGRID_API_KEY");

        Email from = new Email(emailSender);
        String subject = mailSubject;
        Email to = new Email(mailTo);
        Content content = new Content("text/html", mailContent);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}
