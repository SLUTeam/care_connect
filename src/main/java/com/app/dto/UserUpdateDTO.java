package com.app.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserUpdateDTO implements Serializable {

		public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public UUID getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(UUID userRoleId) {
		this.userRoleId = userRoleId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		private static final long serialVersionUID = 1L;
		
		private UUID id;
		private String fullName;
		private String userName;
		//private UserType userType;
		private String phoneNo;
		private String email;
		private String address;
		private UUID userRoleId;	
}
