package com.app.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelValue implements Comparable<LabelValue> {
	
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
