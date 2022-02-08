package com.alkemy.ong.sendgrid.config;
/***
 * 
 * @author Mauro
 *	En esta clase se define el contenido del email para el Request.
 */
public class EmailRequest {
	
	private String to;
	private String subject;
	private String body;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
