package com.app.dto;

import java.io.Serializable;
import java.util.UUID;

import com.app.enumeration.UserType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private UUID userId;
	private String userName;
	private String fullName;
	private String phone;
	private Boolean changePassword;
	private String address;
	private UUID roleId;
    private UserType roleName;

}
