package com.app.response;


public class Error {

	private String code;
	private String Reason;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}
	@Override
	public String toString() {
		return "Error [code=" + code + ", Reason=" + Reason + "]";
	}
	
	
}
