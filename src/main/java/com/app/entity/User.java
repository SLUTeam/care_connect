package com.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.app.util.PasswordUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tb_users")
public class User extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	
	@Column(name = "user_name")
	private String userName;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "email")
	private String email;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "address_line")
	private String address;

	@Type(type = "uuid-char")
	@Column(name = "role_id")
	private UUID userRoleId;

	@Column(name = "change_password")
	private Boolean changePassword;
	
	@JsonIgnore
	@Column(name = "force_password_change")
	private Boolean forcePasswordChange = false;
	
	@JsonIgnore
	@Column(name = "deleted_on")
	private Date deletedOn;

	@JsonIgnore
	@Column(name = "deleted_by")
	private String deletedBy;

	@Transient
	@NotNull(message = "Password")
	@ApiModelProperty(value = "Valid password", required = true, allowableValues = "String")
	private String reqPassword;

	public void setAndEncryptPassword(String password) {
		setPassword(PasswordUtil.getEncryptedPassword(password));
	}

}