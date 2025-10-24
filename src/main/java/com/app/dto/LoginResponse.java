package com.app.dto;

import java.io.Serializable;
import java.util.UUID;

import com.app.enumeration.UserType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse implements Serializable {

	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getChangePassword() {
		return changePassword;
	}
	public void setChangePassword(Boolean changePassword) {
		this.changePassword = changePassword;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public UUID getRoleId() {
		return roleId;
	}
	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}
	public UserType getRoleName() {
		return roleName;
	}
	public void setRoleName(UserType roleName) {
		this.roleName = roleName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
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
