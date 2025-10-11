package com.app.dto;

import com.app.entity.SendEmailDetails;

public class PasswordSendEvent extends SendEmailDetails {

	private static final long serialVersionUID = 1L;

	private String password;

	public PasswordSendEvent(Object source, String password) {
		super(source);
		this.password = password;

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
