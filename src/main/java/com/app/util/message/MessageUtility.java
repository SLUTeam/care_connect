package com.app.util.message;

import com.app.dataobject.ValueObject;
import com.app.util.exception.ExceptionProcess;
import com.app.util.file.PropertiesUtils;

public class MessageUtility {

	/**
	 * to trace class logs
	 */
	public static final String	TRACE_ID	= MessageUtility.class.getName();

	/**
	 * set message of transaction in valueobject
	 *
	 * @param messageCode - code for message description
	 * @param type - message type
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setMessage(int messageCode, short type) throws Exception {

		ValueObject	message				= null;

		try {

			message	= new ValueObject();

			return setMessage(messageCode, type, message);
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			message				= null;
		}
	}
	
	/**
	 * set message of transaction in valueobject
	 *
	 * @param messageCode - code for message description
	 * @param type - message type
	 * @param messageAppend - dynamic message string
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setMessage(int messageCode, String messageAppend,  short type) throws Exception {

		ValueObject	message				= null;

		try {

			message	= new ValueObject();

			return setMessage(messageCode, messageAppend, type, message);
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			message				= null;
		}
	}

	/**
	 * set message of transaction to given valueobject
	 *
	 * @param messageCode - code for message description
	 * @param type - message type
	 * @param object - object which will contain message DTO
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setMessage(int messageCode, short type, ValueObject object) throws Exception {

		String		messageDescription	= null;

		try {

			messageDescription	= getMessgaeString(messageCode);

			object.put(Message.MESSAGE, createMessageDto(messageCode, messageDescription, type));

			return object;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			messageDescription	= null;
		}
	}

	/**
	 * set message of transaction to given valueobject
	 *
	 * @param messageCode - code for message description
	 * @param type - message type
	 * @param object - object which will contain message DTO
	 * @param messageAppend - dynamic message string
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setMessage(int messageCode, String messageAppend, short type, ValueObject object) throws Exception {

		String		messageDescription	= null;

		try {

			if(messageCode > 0) {
				messageDescription	= getMessgaeString(messageCode) + " " + messageAppend;
			} else {
				messageDescription	= messageAppend;
			}

			object.put(Message.MESSAGE, createMessageDto(messageCode, messageDescription, type));

			return object;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			messageDescription	= null;
		}
	}
	
	/**
	 * set success message of transaction to given valueobject
	 *
	 * @param messageCode - code for message description
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setSuccess(int messageCode) throws Exception {
		return setMessage(messageCode, Message.MESSAGE_TYPE_SUCCESS);
	}

	/**
	 * set success message of transaction to given valueobject
	 *
	 * @param messageCode - code for message description
	 * @param object - object which will contain message DTO
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setSuccess(int messageCode, ValueObject object) throws Exception {
		return setMessage(messageCode, Message.MESSAGE_TYPE_SUCCESS, object);
	}

	/**
	 * set error message of transaction to given valueobject
	 *
	 * @param messageCode - code for message description
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setError(int messageCode) throws Exception {
		return setMessage(messageCode, Message.MESSAGE_TYPE_ERROR);
	}
	
	public static ValueObject setInfo(String messageStr) throws Exception {
		return setMessage(messageStr, Message.MESSAGE_TYPE_INFO);
	}
	
	public static ValueObject setError(String messageStr) throws Exception {
		return setMessage(messageStr, Message.MESSAGE_TYPE_ERROR);
	}
	
	public static ValueObject setSuccess(String messageStr) throws Exception {
		return setMessage(messageStr, Message.MESSAGE_TYPE_SUCCESS);
	}
	
	public static ValueObject setWarning(String messageStr) throws Exception {
		return setMessage(messageStr, Message.MESSAGE_TYPE_WARNING);
	}

	/**
	 * set error message of transaction to given valueobject
	 *
	 * @param messageCode   - code for message description
	 * @param messageAppend - code for message description
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setError(int messageCode, String appendMessage) throws Exception {
		return setMessage(messageCode, appendMessage, Message.MESSAGE_TYPE_ERROR);
	}
	
	public static ValueObject setError(int messageCode, ValueObject valueObject, String appendMessage) throws Exception {
		return setMessage(messageCode, appendMessage, Message.MESSAGE_TYPE_ERROR, valueObject);
	}
	
	/**
	 * set error message of transaction to given valueobject
	 *
	 * @param messageCode   - code for message description
	 * @param messageAppend - code for message description
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setInfo(int messageCode, String appendMessage) throws Exception {
		return setMessage(messageCode, appendMessage, Message.MESSAGE_TYPE_INFO);
	}
	
	public static ValueObject setInfo(int messageCode, ValueObject valueObject, String appendMessage) throws Exception {
		return setMessage(messageCode, appendMessage, Message.MESSAGE_TYPE_INFO, valueObject);
	}
	
	/**
	 * set error message of transaction to given valueobject
	 *
	 * @param messageCode   - code for message description
	 * @param messageAppend - code for message description
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setSuccess(int messageCode, String appendMessage) throws Exception {
		return setMessage(messageCode, appendMessage, Message.MESSAGE_TYPE_SUCCESS);
	}
	
	public static ValueObject setSuccess(int messageCode, ValueObject valueObject, String appendMessage) throws Exception {
		return setMessage(messageCode, appendMessage, Message.MESSAGE_TYPE_SUCCESS, valueObject);
	}
	
	/**
	 * set error message of transaction to given valueobject
	 *
	 * @param messageCode   - code for message description
	 * @param messageAppend - code for message description
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setWarning(int messageCode, String appendMessage) throws Exception {
		return setMessage(messageCode, appendMessage, Message.MESSAGE_TYPE_WARNING);
	}
	
	public static ValueObject setWarning(int messageCode, ValueObject valueObject, String appendMessage) throws Exception {
		return setMessage(messageCode, appendMessage, Message.MESSAGE_TYPE_WARNING, valueObject);
	}
	
	/**
	 * set error message of transaction to given valueobject
	 *
	 * @param messageCode - code for message description
	 * @param object - object which will contain message DTO
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setError(int messageCode, ValueObject object) throws Exception {
		return setMessage(messageCode, Message.MESSAGE_TYPE_ERROR, object);
	}

	/**
	 * set info message of transaction to given valueobject
	 *
	 * @param messageCode - code for message description
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setInfo(int messageCode) throws Exception {
		return setMessage(messageCode, Message.MESSAGE_TYPE_INFO);
	}

	/**
	 * set info message of transaction to given valueobject
	 *
	 * @param messageCode - code for message description
	 * @param object - object which will contain message DTO
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setInfo(int messageCode, ValueObject object) throws Exception {
		return setMessage(messageCode, Message.MESSAGE_TYPE_INFO, object);
	}

	/**
	 * set warning message of transaction to given valueobject
	 *
	 * @param messageCode - code for message description
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setWarning(int messageCode) throws Exception {
		return setMessage(messageCode, Message.MESSAGE_TYPE_WARNING);
	}

	/**
	 * set warning message of transaction to given valueobject
	 *
	 * @param messageCode - code for message description
	 * @param object - object which will contain message DTO
	 * @return - valueobject containing message DTO
	 * @throws Exception
	 */
	public static ValueObject setWarning(int messageCode, ValueObject object) throws Exception {
		return setMessage(messageCode, Message.MESSAGE_TYPE_WARNING, object);
	}

	/**
	 * check if message exist
	 *
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public static boolean isMessage(ValueObject object) throws Exception {
		return object.containsKey(Message.MESSAGE);
	}

	/**
	 * check if success message exist
	 *
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public static boolean isSuccess(ValueObject object) throws Exception {
		return isMessage(object);
	}

	/**
	 * check if error message exist
	 *
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public static boolean isError(ValueObject object) throws Exception {
		return isMessage(object);
	}

	/**
	 * check if info message exist
	 *
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public static boolean isInfo(ValueObject object) throws Exception {
		return isMessage(object);
	}

	/**
	 * check if warning message exist
	 *
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public static boolean isWarning(ValueObject object) throws Exception {
		return isMessage(object);
	}

	/**
	 * get message description form file
	 *
	 * @param messageCode - code for message description
	 * @return - message description
	 * @throws Exception
	 */
	public static String getMessgaeString(int messageCode) throws Exception {
		return PropertiesUtils.getValueByName(MessageList.MESSAGE_FILE_PATH, messageCode + "");
	}

	/**
	 * set data into message dto
	 *
	 * @param messageCode - message Code
	 * @param messageDescription - message Description
	 * @param type - message type
	 * @return message DTO
	 * @throws Exception
	 */
	private static Message createMessageDto(int messageCode, String messageDescription, short type) throws Exception {
		Message	message	= null;
		
		try {
			message	= new Message();
			
			message.setMessageId(messageCode);
			message.setDescription(messageDescription);
			message.setType(type);
			message.setTypeName(Message.getTypeName(type));
			message.setTypeSymble(Message.getTypeSymble(type));
			
			return message;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			message	= null;
		}
	}
	
	public static ValueObject setMessage(String messageStr, short type) throws Exception {
		ValueObject	message				= null;

		try {

			message	= new ValueObject();
			
			message.put(Message.MESSAGE, createMessageDto(0, messageStr, type));

			return message;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			message				= null;
		}
	}
}
