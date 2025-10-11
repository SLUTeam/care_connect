package com.app.util.exception;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;


public class ExceptionProcess {

	private static final String TRACE_ID = ExceptionProcess.class.getName();

	private static final String EXCEPTION_FILE_NAME	= "../../logs/LICException";
	private static final Logger logger = Logger.getLogger(ExceptionProcess.class);

	public static Exception execute(Exception exception, String traceid) {
		try {
			logger.info(exception);
		} catch (Exception e) {
			logger.info(e);
		}
		return exception;
	}

	public static Exception execute(Exception exception, String traceid, Object object) {
		try {
			ExceptionProcess.execute(exception, traceid);
		} catch (Exception e) {
			logger.info(e);
		}
		return exception;
	}

	public static boolean isColumnThereInResultSet(ResultSet rs, String column){
 	    
 		try {
 	    	System.out.println("column >>"+column);
 	        rs.findColumn(column);
 	        return true;
 	    } catch (SQLException sqlex){
 	    	logger.debug("column doesn't exist {}"+ column);
 	    }
 	    return false;
 	}
	//private static Logger getExceptionLogger() throws Exception {
	//	return LogWriter.getLogger(ExceptionProcess.EXCEPTION_FILE_NAME);
	//}
}
