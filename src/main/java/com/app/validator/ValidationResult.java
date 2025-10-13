package com.app.validator;

import java.util.List;

import lombok.Data;

@Data
public class ValidationResult {

	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	private boolean isValid =true;
	private List<String> errors;
	private Object object;
}
