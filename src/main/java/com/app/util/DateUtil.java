package com.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtil {

	static final String DATE_TIME_FORMAT_DB = "yyyy-MM-dd HH:mm:ss";
	static final String DATE_FORMAT_DB = "yyyy-MM-dd";
	static final String VIEW_DATE_FORMAT = "dd/MM/yyyy hh:mm aa";
	static final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	static final DateFormat dbDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static final DateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	static final DateFormat dateTimeFormatReceivingFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a");

	static final DateFormat dateTimeDisplayFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm a");
	
	static final DateFormat yearLastTwoDigitsDF = new SimpleDateFormat("yy"); // Just the year, with 2 digits

	public static Date getUTCdatetimeAsDate() {
		return stringDateToDate(getUTCdatetimeAsString());
	}

	public static String getUTCdatetimeAsString() {
		final SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_DB);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		final String utcTime = sdf.format(new Date());
		return utcTime;
	}

	public static Date stringDateToDate(String StrDate) {
		Date dateToReturn = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_DB);
		try {
			dateToReturn = (Date) dateFormat.parse(StrDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateToReturn;
	}

	public static String getViewDateFormat(String dateStr) {
		SimpleDateFormat dbFormat = new SimpleDateFormat(DATE_TIME_FORMAT_DB);
		Date dbDate = null;
		try {
			dbDate = dbFormat.parse(dateStr);
			SimpleDateFormat dateFormat = new SimpleDateFormat(VIEW_DATE_FORMAT);
			return dateFormat.format(dbDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getViewDateFormat(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(VIEW_DATE_FORMAT);
		return dateFormat.format(date);
	}

	public static Date getDateFromInputDate(String stDate) {
		Date dateToReturn = null;
		try {
			dateToReturn = inputDateFormat.parse(stDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateToReturn;
	}

	public static String getStringFormatFromDbDate(String dateStr) {
		String returnString = null;
		SimpleDateFormat dbFormat = new SimpleDateFormat(DATE_TIME_FORMAT_DB);
		Date dbDate = null;
		try {
			dbDate = dbFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnString = inputDateFormat.format(dbDate);
		return returnString;
	}

	public static Date parseDate(String dateStr) {
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String formatString(Date date) {
		return dateFormat.format(date);
	}

	public static String formatDateTimeToString(Date date) {
		return dateTimeDisplayFormat.format(date);
	}

	public static Date parseDateWithTime(String dateStr) {
		try {
			return dateTimeFormatReceivingFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getFormatedDateFromDbDateTimeFormat(String dateStr) {
		String returnString = null;
		Date dbDate = null;
		try {
			dbDate = dbDateTimeFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnString = dateTimeDisplayFormat.format(dbDate);
		return returnString;
	}

	public static String getFormatedDateFromDbDateFormat(String dateStr) {
		String returnString = null;
		Date dbDate = null;
		try {
			dbDate = dbDateFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnString = dateFormat.format(dbDate);
		return returnString;
	}

	public static String convertToDbDateFormat(Date date) {
		return dbDateFormat.format(date);
	}

	public static LocalDate getNextDayOfWeekDate(Date date, DayOfWeek dayOfWeek) {
		LocalDate dt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate destinationDate = dt.with(TemporalAdjusters.next(dayOfWeek));
		return destinationDate;
	}

	public static void mainFinal(String[] args) {

		LocalDate destinationDate = getNextDayOfWeekDate(new Date(), DayOfWeek.MONDAY);
		Date recentMonday = Date.from(destinationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		LocalDate destinationDate1 = getNextDayOfWeekDate(recentMonday, DayOfWeek.MONDAY);

		System.out.println(destinationDate + "-------" + destinationDate1);
	}

	public static void mainFindNextDayFromDayOfWeek(String[] args) {
		LocalDate dt = LocalDate.now();
		System.out.println("\nNext Friday: " + dt.with(TemporalAdjusters.next(DayOfWeek.TUESDAY)));
		System.out.println("Previous Friday: " + dt.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)) + "\n");
	}

	public static void mainold(String args[]) {
		LocalDate localDate = LocalDate.now();

		SimpleDateFormat sdf = new SimpleDateFormat("EEE");
		System.out.println(sdf.format(localDate));

	}

	public static String getETAOrETDFormat(Date date) {
		String parsedDate = dateFormat.format(date);
		parsedDate = parsedDate.substring(0, parsedDate.length() - 5);
		return parsedDate;
	}

	public static Integer noOfdaysBetweenTwoDateStr(String fromDateStr, String toDateStr) {
		Date fromDate = parseDate(fromDateStr);
		Date toDate = parseDate(toDateStr);
		return (int) ((fromDate.getTime() - toDate.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static Date getNewDateAddingDays(Date fromDate, Integer noOfDays) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(fromDate);
		calendar.set(Calendar.DAY_OF_MONTH, noOfDays);
		return calendar.getTime();
	}
	
	public static String getShortYear() {
		String formattedDate = yearLastTwoDigitsDF.format(Calendar.getInstance().getTime());
		return formattedDate;
	}

}
