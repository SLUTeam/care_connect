package com.app.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelValue implements Comparable<LabelValue> {
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String label;
	private String value;
	
	public LabelValue(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}
	
	@Override
	public int compareTo(LabelValue o) {
		return getLabel().compareTo(o.getLabel());
	}
}
