package com.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateConversionUtil {

	static final DateFormat DB_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static final DateFormat DB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	static final DateFormat DISPLAY_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	static final DateFormat DISPLAY_DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy hh:mm aa");
	
	static final DateFormat RECEIVING_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	static final DateFormat RECEIVING_DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a");
	

	public static String getDisplayFormatDate(Date date) {
		return DISPLAY_FORMAT.format(date);
	}
	
	public static String formatDate(Date date) {
		return RECEIVING_DATE_FORMAT.format(date);
	}

	public static Date parseDate(String dateStr) {
		try {
			return RECEIVING_DATE_FORMAT.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
 
	
	public static String getDisplayFormatDateTime(Date date) {
		return DISPLAY_DATE_TIME_FORMAT.format(date);
	}
	
	public static String formatDateTime(Date date) {
		return RECEIVING_DATE_TIME_FORMAT.format(date);
	}

	public static Date parseDateTime(String dateStr) {
		try {
			return RECEIVING_DATE_TIME_FORMAT.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static void main(String[] args) {
		Calendar currentdate = Calendar.getInstance();
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		TimeZone obj = TimeZone.getTimeZone("Asia/Hong_Kong");
		formatter.setTimeZone(obj);
		System.out.println("Local:: " +currentdate.getTime());
		System.out.println("HONG KONG:: "+ formatter.format(currentdate.getTime()));
		
		 
	}

	 
}
