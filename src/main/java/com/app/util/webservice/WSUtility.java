package com.app.util.webservice;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.app.dataobject.ValueObject;
import com.app.util.exception.ExceptionProcess;
import com.app.util.file.PropertiesUtils;
import com.app.util.message.MessageList;
import com.app.util.message.MessageUtility;

public class WSUtility {

	public static final String		TRACE_ID	= WSUtility.class.getName();

	public static final String WEB_SERVICE_RESULT 		= "serviceResult";
	public static final String WEB_SERVICE_RESULT_FAIL  = "serviceResultFail";
	public static final String INVALID_TOKEN  			= "401";

	public static ValueObject callWebService(String uri) throws Exception {

		String				output		= null;

		URL					url			= null;
		HttpURLConnection	connection	= null;
		BufferedReader		br			= null;

		ValueObject			valObjWS	= null;

		try {

			if (uri == null || uri.equals("")) {
				return MessageUtility.setError(MessageList.WEB_SERVICE_URL_EMPTY);
			}

			url	= new URL(uri);

			connection	= (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			if (connection == null || connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
			//	LogWriter.writeLog(TRACE_ID, LogWriter.LOG_LEVEL_DEBUG, "Exception - Connection failed for URL(" + uri + ") for Con-Ewaybill : " + WSUtility.WEB_SERVICE_RESULT_FAIL);
				return MessageUtility.setError(MessageList.WEB_SERVICE_FAILED);
			}

			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			output = br.readLine();

			valObjWS	= new ValueObject();
			valObjWS.put(WSUtility.WEB_SERVICE_RESULT, output);

			return valObjWS;
		} catch (Exception e) {
			//LogWriter.writeLog(TRACE_ID, LogWriter.LOG_LEVEL_DEBUG, "Exception Occured While Updating Con-Ewaybill");
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {

			if (connection != null) {
				connection.disconnect();
			}

			if (br != null) {
				br.close();
			}

			output		= null;
			url			= null;
			connection	= null;
			br			= null;

			if (valObjWS == null || !valObjWS.containsKey(WSUtility.WEB_SERVICE_RESULT)) {
				return MessageUtility.setError(MessageList.WEB_SERVICE_FAILED);
			}

			valObjWS	= null;
		}
	}

	public static ValueObject callPostWebService(String uri, String urlParameters) throws Exception {
		
		String				output		= null;
		URL					url			= null;
		HttpURLConnection	connection	= null;
		BufferedReader		br			= null;
		ValueObject			valObjWS	= null;
		DataOutputStream	dos			= null;
		
		try {
			
			if (uri == null || uri.equals("")) {
				return MessageUtility.setError(MessageList.WEB_SERVICE_URL_EMPTY);
			}
			
			url	= new URL(uri);
			
			connection	= (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
		
			dos	= new DataOutputStream(connection.getOutputStream());
			dos.writeBytes(urlParameters);
			dos.flush();
			dos.close();
			
			if (connection == null || connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return MessageUtility.setError(MessageList.WEB_SERVICE_FAILED);
			}
			
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			output = br.readLine();
			
			valObjWS	= new ValueObject();
			valObjWS.put(WSUtility.WEB_SERVICE_RESULT, output);
			
			return valObjWS;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			
			if (connection != null) {
				connection.disconnect();
			}
			
			if (br != null) {
				br.close();
			}
			
			output		= null;
			url			= null;
			connection	= null;
			br			= null;
			
			if (valObjWS == null || !valObjWS.containsKey(WSUtility.WEB_SERVICE_RESULT)) {
				return MessageUtility.setError(MessageList.WEB_SERVICE_FAILED);
			}
			
			valObjWS	= null;
		}
	}

	public static String getWebServiceUrl(String propertyName) throws Exception {
		try {
			return PropertiesUtils.getValueByName(WebServiceURI.WEB_SERVICES_HOST_PATH, WebServiceURI.WEB_SERVICES_HOST_URL)+PropertiesUtils.getValueByName(WebServiceURI.WEB_SERVICES_URL_PATH, propertyName);
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		}
	}
	
	public static ValueObject callPostWebServiceWithJSON(String uri, String urlParameters) throws Exception {
		
		String				output		= null;
		URL					url			= null;
		HttpURLConnection	connection	= null;
		BufferedReader		br			= null;
		ValueObject			valObjWS	= null;
		DataOutputStream	dos			= null;
		
		try {
			
			if (uri == null || uri.equals("")) {
				return MessageUtility.setError(MessageList.WEB_SERVICE_URL_EMPTY);
			}
			url	= new URL(uri);
			connection	= (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			
			dos	= new DataOutputStream(connection.getOutputStream());
			dos.write(urlParameters.toString().getBytes("UTF-8"));
			dos.flush();
			dos.close();
			
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			output = br.readLine();
			
			valObjWS	= new ValueObject();
			valObjWS.put(WSUtility.WEB_SERVICE_RESULT, output);
			
			return valObjWS;
		} catch (IOException i) {
			
			valObjWS	= new ValueObject();
			if(i.getMessage().contains(INVALID_TOKEN)) {
				
				valObjWS.put(WSUtility.WEB_SERVICE_RESULT_FAIL, INVALID_TOKEN);
			}
			
			return valObjWS;
				
			
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			
			if (connection != null) {
				connection.disconnect();
			}
			
			if (br != null) {
				br.close();
			}
			if (dos != null) {
				dos.close();
			}
			
			output		= null;
			url			= null;
			connection	= null;
			br			= null;
			
			if (valObjWS == null || (!valObjWS.containsKey(WSUtility.WEB_SERVICE_RESULT) && !valObjWS.containsKey(WSUtility.WEB_SERVICE_RESULT_FAIL))) {
				return MessageUtility.setError(MessageList.WEB_SERVICE_FAILED);
			}
			
			valObjWS	= null;
		}
	}
}
