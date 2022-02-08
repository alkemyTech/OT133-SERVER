package com.alkemy.ong.sendgrid.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
/***
 * 
 * @author Mauro
 *Logíca para el envío de mail al contacto registrado.
 */
@Service
public class EmailService {
	/**
	 * @author Mauro
	 * Este método pertenece a la dependencia de SendGrid.
	 */
	@Autowired
	SendGrid sendGrid;
	
	public Response sendMail(EmailRequest emailResquest) {
		Mail mail = new Mail(new Email("alkemysomosmas@gmail.com"), emailResquest.getSubject(), 
				new Email(emailResquest.getTo()), new Content("text/plain", emailResquest.getBody()));
		
		mail.setReplyTo(new Email("alkemysomosmas@gmail.com"));
		Request request = new Request();
		
		Response response = null;
		
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			
			response = this.sendGrid.api(request);
			
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
		return response;
	}
}
