package com.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpPhoneRegisterDto {
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String email;

}
