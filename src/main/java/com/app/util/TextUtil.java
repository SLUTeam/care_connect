package com.app.util;

import java.util.UUID;

public class TextUtil {

	public static Boolean isContains(String description, String searchKey) {
		if (null != searchKey && (null != description && description.toLowerCase().contains(searchKey.toLowerCase()))) {
			return true;
		}
		return false;
	}

	public static Boolean isContainsInt(Integer description, Integer searchKey) {
		if (null != searchKey && null != description) {
			return true;
		}
		return false;
	}

	public static Boolean isEmptyOrNull(String searchKey) {
		if (null == searchKey || searchKey.isEmpty()) {
			return true;
		}
		return false;
	}

	public static Boolean isEmptyOrNullInt(Integer searchKey) {
		if (null == searchKey) {
			return true;
		}
		return false;
	}

	public static Boolean isEmptyOrNull(UUID id) {
		if (null == id) {
			return true;
		}
		return false;
	}

}
