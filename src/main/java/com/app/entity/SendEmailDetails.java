package com.app.entity;

import java.io.Serializable;

import org.springframework.context.ApplicationEvent;

public class SendEmailDetails extends ApplicationEvent implements Serializable {

	public SendEmailDetails(Object source) {
		super(source);
	}
	
	private static final long serialVersionUID = 1L;
	
	public String emailTo;
	public String bcc;
	public String cc;
	
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	
}
