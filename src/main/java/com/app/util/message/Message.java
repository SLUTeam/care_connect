package com.app.util.message;

import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String	MESSAGE			= "message";
	public static final String	MESSAGE_ID		= "messageId";
	public static final String	DESCRIPTION		= "description";
	public static final String	TYPE			= "type";
	public static final String	TYPE_NAME		= "typeName";
	
	public static final String 	PRICING_POSSIBLE 	 = "Booking trade deficit is greater than -50. You are now eligible to create quotation.";
	public static final String 	PRICING_NOT_POSSIBLE = "Booking trade deficit is less than -50. You are not eligible to create quotation.";
	public static final String 	ENDURANCE_POSSIBLE 	 = "Booking trade deficit is greater than 100. You are now eligible to create quotation.";
	public static final String 	ENDURANCE_NOT_POSSIBLE = "Booking trade deficit is less than 100. You are not eligible to create quotation.";
		
	public static final short	MESSAGE_TYPE_SUCCESS	= 1;
	public static final short	MESSAGE_TYPE_ERROR		= 2;
	public static final short	MESSAGE_TYPE_INFO		= 3;
	public static final short	MESSAGE_TYPE_WARNING	= 4;

	public static final String	MESSAGE_TYPE_SUCCESS_NAME	= "success";
	public static final String	MESSAGE_TYPE_ERROR_NAME		= "error";
	public static final String	MESSAGE_TYPE_INFO_NAME		= "info";
	public static final String	MESSAGE_TYPE_WARNING_NAME	= "warning";
	
	public static final String	MESSAGE_TYPE_SUCCESS_SYMBLE		= "<i class='fa fa-check'></i>";
	public static final String	MESSAGE_TYPE_ERROR_SYMBLE		= "<i class='fa fa-times-circle'></i>";
	public static final String	MESSAGE_TYPE_INFO_SYMBLE		= "<i class='fa fa-info-circle'></i>";
	public static final String	MESSAGE_TYPE_WARNING_SYMBLE		= "<i class='fa fa-warning'></i>";

	private static HashMap<Short, String> messageHM 		= new HashMap<Short, String>();
	private static HashMap<Short, String> messageSymbleHM 	= new HashMap<Short, String>();

	static {
		messageHM.put(MESSAGE_TYPE_SUCCESS, MESSAGE_TYPE_SUCCESS_NAME);
		messageHM.put(MESSAGE_TYPE_ERROR, MESSAGE_TYPE_ERROR_NAME);
		messageHM.put(MESSAGE_TYPE_INFO, MESSAGE_TYPE_INFO_NAME);
		messageHM.put(MESSAGE_TYPE_WARNING, MESSAGE_TYPE_WARNING_NAME);
		
		messageSymbleHM.put(MESSAGE_TYPE_SUCCESS, MESSAGE_TYPE_SUCCESS_SYMBLE);
		messageSymbleHM.put(MESSAGE_TYPE_ERROR, MESSAGE_TYPE_ERROR_SYMBLE);
		messageSymbleHM.put(MESSAGE_TYPE_INFO, MESSAGE_TYPE_INFO_SYMBLE);
		messageSymbleHM.put(MESSAGE_TYPE_WARNING, MESSAGE_TYPE_WARNING_SYMBLE);
	}

	private int messageId;
	private String description;
	private short type;
	private String typeName;
	private String typeSymble;

	/**
	 * @return the messageId
	 */
	public int getMessageId() {
		return messageId;
	}
	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the type
	 */
	public short getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(short type) {
		this.type = type;
	}
	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 *
	 * @param type the typeName to get
	 * @return the typeName
	 */
	public static String getTypeName(short type) {
		return messageHM.get(type);
	}
	/**
	 * @return the typeSymble
	 */
	public String getTypeSymble() {
		return typeSymble;
	}
	/**
	 * @param typeSymble the typeSymble to set
	 */
	public void setTypeSymble(String typeSymble) {
		this.typeSymble = typeSymble;
	}
	
	public static String getTypeSymble(short type) {
		return messageSymbleHM.get(type);
	}
}
