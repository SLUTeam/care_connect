
package com.app.util;


import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.app.dataobject.ValueObject;
import com.app.util.exception.ExceptionProcess;

public abstract class DateTimeUtility {

	public static final String	TRACE_ID	= DateTimeUtility.class.getName();

	public static final String	DD_MM_YYYY				= "dd-MM-yyyy";

	public static final String	DDMMYYYY				= "dd/MM/yyyy";

	public static final String	HH_MM_AA				= "hh:mm aa";

	public static final String	H_MM_AA					= "h:mm aa";

	public static final String DD_MM_YY					= "dd-MM-yy";

	public static final String DD_MM_YY_HH_MM_AA		= "dd-MM-yy hh:mm aa";

	public static final String DD_MM_YYYY_HH_MM_AA		= "dd-MM-yyyy hh:mm aa";
	
	public static final String YYYY_MM_DD_HH_MM_AA		= "yyyy-MM-dd HH:mm:ss";

	public static final String DD_MM_YYYY_HH_MM_AA_WITHOUT_CHARACTERS		= "dd MM yyyy hh mm aa";

	public static final String DD_MM_YYYY_HH_MM_SS_AA	= "dd-MM-yyyy hh:mm:ss aa";

	public static final String DD_MM_YY_HH_MM_SS		= "dd-MM-yyyy HH:mm:ss";

	public static final String YYYY						= "yyyy";

	public static final String MM						= "MM";

	public static final String MMM						= "MMM";

	public static final String START_OF_MONTH			= "startOfMonth";

	public static final String END_OF_MONTH				= "endOfMonth";

	public static final String START_OF_WEEK			= "startOfWeek";

	public static final String END_OF_WEEK				= "endOfWeek";

	public static final int	MILLIS_IN_DAY				= 1000 * 60 * 60 * 24;

	public static String[] fieldsName = { "YEAR", "MONTH", "HOUR", "MINUTE", "SECOND", "DAY_OF_WEEK", "DAY_OF_MONTH", "DAY_OF_YEAR",
			"WEEK_OF_MONTH", "DAY_OF_WEEK_IN_MONTH", "WEEK_OF_YEAR", "TIMEZONE" };

	/**
	 * Current date of system
	 *
	 * @return - system current date
	 */
	public static Date getCurrentDate() {
		return Date.valueOf(LocalDate.now());
	}

	/**
	 * Current timestamp of system
	 *
	 * @return - system current timestamp
	 */
	public static Timestamp getCurrentTimeStamp() {
		return Timestamp.from(Instant.now());
	}

	/**
	 * TimeStamp form string data
	 *
	 * @param date - date in string format
	 * @return - return timestamp of given date
	 * @throws Exception
	 */
	public static Timestamp getTimeStamp(String date) throws Exception {

		SimpleDateFormat	dateFormat		= null;
		java.util.Date		parsedTimeStamp	= null;
		Timestamp			timestamp		= null;

		try {
			dateFormat		=	new SimpleDateFormat(DD_MM_YYYY);
			parsedTimeStamp	=	dateFormat.parse(date);
			timestamp		=	new Timestamp(parsedTimeStamp.getTime());
			return timestamp;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			dateFormat		= null;
			parsedTimeStamp	= null;
			timestamp		= null;
		}
	}

	public static Timestamp getTimeStampFromStringWithAMPM(String date) throws Exception {

		SimpleDateFormat	dateFormat		= null;
		java.util.Date		parsedTimeStamp	= null;
		Timestamp			timestamp		= null;

		try {
			dateFormat		=	new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS_AA);
			parsedTimeStamp	=	dateFormat.parse(date);
			timestamp		=	new Timestamp(parsedTimeStamp.getTime());
			return timestamp;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			dateFormat		= null;
			parsedTimeStamp	= null;
			timestamp		= null;
		}
	}

	public static Timestamp getTimeStampFromString(String date) throws Exception {

		SimpleDateFormat	dateFormat		= null;
		java.util.Date		parsedTimeStamp	= null;
		Timestamp			timestamp		= null;

		try {
			dateFormat		=	new SimpleDateFormat(DDMMYYYY);
			parsedTimeStamp	=	dateFormat.parse(date);
			timestamp		=	new Timestamp(parsedTimeStamp.getTime());
			return timestamp;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			dateFormat		= null;
			parsedTimeStamp	= null;
			timestamp		= null;
		}
	}

	/**
	 * Create custom date form year, month, day
	 *
	 * @param year - to be set
	 * @param month - to be set
	 * @param day - to be set
	 * @return - generated custom date
	 */
	public static Date getCustomDate(int year, int month, int day) {
		return Date.valueOf(LocalDate.of(year, month, day));
	}

	/**
	 * Timestamp to date in DD-MM-YYYY format
	 *
	 * @param timeStamp - to format into date
	 * @return - formated date of given timestamp
	 */
	public static String getDateFromTimeStamp(Timestamp timeStamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY);
		return dateFormat.format(timeStamp);
	}

	public static String getCurrentDateString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY);
		return dateFormat.format(getCurrentTimeStamp().getTime());
	}

	public static String getDateFromTimeStampWithAMPM(Timestamp timeStamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS_AA);
		return dateFormat.format(timeStamp);
	}

	/**
	 * Timestamp to time in HH-MM-AA format
	 *
	 * @param timeStamp - to format into date
	 * @return - formated time of given timestamp
	 */
	public static String getTimeFromTimeStamp(Timestamp timeStamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(H_MM_AA);
		return dateFormat.format(timeStamp);
	}
	public static String getTimeFromTimeStampIn24HourFormat(Timestamp timeStamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm:ss");
		return dateFormat.format(timeStamp);
	}

	/**
	 * Timestamp to date in DD-MM-YYYY HH:MM AA format
	 *
	 * @param timeStamp - to format into date
	 * @return - formated date of given timestamp
	 */
	public static String getDateTimeFromTimeStamp(Timestamp timeStamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DD_MM_YY_HH_MM_AA);
		return dateFormat.format(timeStamp);
	}

	/**
	 * Timestamp to date in given format
	 *
	 * @param timeStamp - to format into date
	 * @param dateFormat - custom date format
	 * @return - formated date of given timestamp
	 */
	public static String getDateFromTimeStamp(Timestamp timeStamp, String dateFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.format(timeStamp);
	}

	public static Timestamp getStartOfDayTimeStamp(String date) throws Exception {

		try {
			return getCalendarTimeFromTimestamp(getTimeStamp(date), 0, 0, 0, 0);
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} 
	}

	public static Timestamp getEndOfDayTimeStamp(String date) throws Exception {
		try {
			return getCalendarTimeFromTimestamp(getTimeStamp(date), 23, 59, 59, 998);
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} 
	}

	public static Timestamp appendTimeToDate(String date) throws Exception {
		Calendar 			currentDateTime 	= null;
		SimpleDateFormat	sdf					= null;
		try {
			currentDateTime 	= Calendar.getInstance();
			sdf    				= new SimpleDateFormat(DD_MM_YY_HH_MM_SS);
			return new Timestamp((sdf.parse(date + " " + currentDateTime.get(Calendar.HOUR_OF_DAY) + ":" + currentDateTime.get(Calendar.MINUTE) + ":" + currentDateTime.get(Calendar.SECOND))).getTime());
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		}
	}

	public static Timestamp getCalendarTimeFromTimestamp(Timestamp timestamp,int hour,int minute,int second,int millisecond) throws Exception {
		Calendar 	calendar 	= null ;
		Timestamp 	timestamp2 	= null;

		try {
			calendar = Calendar.getInstance();
			calendar.setTime(timestamp);

			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, second);
			calendar.set(Calendar.MILLISECOND, millisecond);	

			timestamp2 = new Timestamp(calendar.getTimeInMillis());

			return timestamp2;	

		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			calendar 	= null ;
			timestamp2 	= null;
		}	
	}

	public static Timestamp getFirstDayTimestampOfMonth() throws Exception {
		Calendar 	calendar 	= null ;
		Timestamp 	timestamp2 	= null;

		try {
			calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH,1);

			timestamp2 = new Timestamp(calendar.getTimeInMillis());

			return timestamp2;	

		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			calendar 	= null ;
			timestamp2 	= null;
		}	
	}

	public static boolean checkDateExistsBetweenDates(Timestamp fromDate, Timestamp toDate, Timestamp transDate) throws Exception {
		boolean			isDuplicate	 = false;

		try {

			if((transDate.after(fromDate) && transDate.before(toDate))){
				isDuplicate = true;
			}
			return isDuplicate;

		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
		}
	}


	public static boolean isEqualFiscalYear(LocalDateTime startDate, LocalDateTime endDate) {
		try {
			if (getFiscalYear(startDate) == getFiscalYear(endDate)) return true;
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return false;
	}

	public static boolean isEqualMonth(LocalDateTime startDate, LocalDateTime endDate) {
		try {
			if (getFiscalYear(startDate) == getFiscalYear(endDate) && startDate.getMonth().compareTo(endDate.getMonth()) == 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return false;
	}

	public static long getFiscalYear(LocalDateTime date) {

		long	fiscalYear	= 0;

		try {

			if (date.getMonth().compareTo(Month.APRIL) < 0) {
				fiscalYear	= Long.parseLong((date.getYear() - 1) + "" + date.getYear());
			} else {
				fiscalYear	= Long.parseLong(date.getYear() + "" + (date.getYear() + 1));
			}

		} catch (Exception e) {
			e.printStackTrace();;
		}

		return fiscalYear;
	}

	public static long getDayDifference(LocalDateTime startDate, LocalDateTime endDate) {
		return ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));
	}

	public static long getMonthDifference(LocalDateTime startDate, LocalDateTime endDate) {
		return ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), endDate.withDayOfMonth(endDate.toLocalDate().lengthOfMonth()).plusDays(1));
	}

	public static long getFiscalYearDifference(LocalDateTime startDate, LocalDateTime endDate) throws Exception {

		LocalDateTime		yearStartDate	= null;
		LocalDateTime		yearEndDate		= null;

		Period			period			= null;

		long			yearDiff		= 0;

		try {

			if (startDate.getMonth().compareTo(Month.APRIL) < 0) {
				yearStartDate	= LocalDateTime.of(startDate.getYear() - 1, Month.APRIL, 1, 0, 0, 0, 0);
			} else {
				yearStartDate	= LocalDateTime.of(startDate.getYear(), Month.APRIL, 1, 0, 0, 0, 0);
			}

			if (endDate.getMonth().compareTo(Month.APRIL) < 0) {
				yearEndDate	= LocalDateTime.of(endDate.getYear(), Month.MARCH, 31, 0, 0, 0, 0);
			} else {
				yearEndDate	= LocalDateTime.of(endDate.getYear() + 1, Month.MARCH, 31, 0, 0, 0, 0);
			}

			period		= Period.between(yearStartDate.toLocalDate(), yearEndDate.toLocalDate().plusDays(1)); // Added 1 Day because it does not count last date

			yearDiff	= period.getYears();

			return yearDiff;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			yearStartDate	= null;
			yearEndDate		= null;
			period			= null;
		}
	}

	public static Timestamp getStartDateofFiscalYear(LocalDate date) throws Exception {
		Date			utilDate	= null;
		Calendar 		cal			= null;

		try {
			if (date.getMonth().compareTo(Month.APRIL) < 0) {
				utilDate = getCustomDate(date.getYear()- 1, Month.APRIL.getValue(), 1);
			} else {
				utilDate = getCustomDate(date.getYear(), Month.APRIL.getValue(), 1);
			}

			cal = Calendar.getInstance();
			cal.setTime(utilDate);
			cal.set(Calendar.MILLISECOND, 0);

			return new java.sql.Timestamp(utilDate.getTime());

		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			utilDate		= null;
			cal				= null;
		}
	}

	public static long getMonthTableId(LocalDateTime date) {
		return Long.parseLong(date.getYear() + "" + date.getMonthValue());
	}

	public static List<Long> getMonthTableIdList(LocalDateTime startDate, LocalDateTime endDate) {
		List<Long>		monthList		= null;
		long			monthDiff		= 0;

		try {

			monthList	= new ArrayList<Long>();

			monthDiff	= getMonthDifference(startDate, endDate);

			for (int i = 0; i < monthDiff; i++) {
				monthList.add(getMonthTableId(startDate.plusMonths(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return monthList;
	}

	public static long getYearTableId(LocalDateTime date) {
		return getFiscalYear(date);
	}

	public static List<Long> getFiscalYearTableIdList(LocalDateTime startDate, LocalDateTime endDate) {
		List<Long>		yearList		= null;
		long			yearDiff		= 0;

		try {

			yearList	= new ArrayList<Long>();

			yearDiff	= getFiscalYearDifference(startDate, endDate);

			for (int i = 0; i < yearDiff; i++) {
				yearList.add(getYearTableId(startDate.plusYears(i)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return yearList;
	}

	public static boolean isFiscalYearFirstDate(LocalDateTime startDate) {
		LocalDateTime		yearStartDate	= null;

		try {

			if (startDate.getMonth().compareTo(Month.APRIL) < 0) {
				yearStartDate	= LocalDateTime.of(startDate.getYear() - 1, Month.APRIL, 1, 0, 0, 0, 0);
			} else {
				yearStartDate	= LocalDateTime.of(startDate.getYear(), Month.APRIL, 1, 0, 0, 0, 0);
			}

			if (startDate.equals(yearStartDate)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			yearStartDate	= null;
		}

		return false;
	}

	public static boolean isFiscalYearLastDate(LocalDateTime endDate) {
		LocalDateTime		yearEndDate	= null;

		try {

			if (endDate.getMonth().compareTo(Month.APRIL) < 0) {
				yearEndDate	= LocalDateTime.of(endDate.getYear(), Month.MARCH, 31, 0, 0, 0, 0);
			} else {
				yearEndDate	= LocalDateTime.of(endDate.getYear() + 1, Month.MARCH, 31, 0, 0, 0, 0);
			}

			if (endDate.equals(yearEndDate)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			yearEndDate	= null;
		}

		return false;
	}

	public static String getDateBeforeNoOfDays(int noOfDays) throws Exception {
		String previousDate		= null;

		try {
			Calendar now = Calendar.getInstance();

			now.add(Calendar.DATE, -noOfDays);

			previousDate	= now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR);

			return previousDate;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			previousDate		= null;
		}
	}

	public static long getDayDiffBetweenTwoDates (Timestamp fromDate, Timestamp tillDate) throws Exception {

		Calendar calTillDate	= null;
		Calendar calFromDate	= null;
		long 	 dayDiff		= 0;	

		try {
			calFromDate = Calendar.getInstance();
			calTillDate = Calendar.getInstance();

			calFromDate.setTime(fromDate);
			calFromDate.set(Calendar.HOUR_OF_DAY, 0);
			calFromDate.set(Calendar.MINUTE, 0);
			calFromDate.set(Calendar.SECOND, 0);
			calFromDate.set(Calendar.MILLISECOND, 0);

			calTillDate.setTime(tillDate);
			calTillDate.set(Calendar.HOUR_OF_DAY, 0);
			calTillDate.set(Calendar.MINUTE, 0);
			calTillDate.set(Calendar.SECOND, 0);
			calTillDate.set(Calendar.MILLISECOND, 0);

			dayDiff = ((calTillDate.getTimeInMillis() - calFromDate.getTimeInMillis())/(1000*60*60*24));

			return	dayDiff;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally{
			calTillDate	= null;
			calFromDate	= null;
		}
	}


	public static String getYesterdayDate() throws Exception {
		SimpleDateFormat	dateFormat	= null;
		Calendar 			calender	= null;

		try {
			dateFormat = new SimpleDateFormat(DD_MM_YYYY);
			calender = Calendar.getInstance();
			calender.add(Calendar.DATE, -1);

			return dateFormat.format(calender.getTime());			
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}
	
	public static String getPreviousDateByMonth(Timestamp timeStamp,int month) throws Exception {
		SimpleDateFormat	dateFormat						= null;	
		try {
				if (timeStamp == null) {
					return null;
				}
				dateFormat 					= new SimpleDateFormat("dd-MM-yyyy");
				Calendar calendar 			= Calendar.getInstance();
				calendar.setTime(timeStamp);
				int lastMonth  				= DateTimeUtility.getMonthFromDate(timeStamp)-month;
				calendar.set(Calendar.MONTH, lastMonth);
				calendar.set(Calendar.DATE, 1);
				
				java.util.Date 	utilDate 	= calendar.getTime();
				java.sql.Date 	sDate 		= new java.sql.Date(utilDate.getTime());
				return dateFormat.format(sDate);
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
		}
	}

	public static ValueObject getPreviousMonthStartAndEndDate() throws Exception {
		Calendar			aCalendar					= null;
		java.util.Date 		firstDateOfPreviousMonth	= null;
		java.util.Date 		lastDateOfPreviousMonth		= null;
		String				startOfMonth				= null;
		String				endOfMonth					= null;
		ValueObject			valueObject					= null;
		SimpleDateFormat 	sdf							= null;

		try {
			aCalendar = Calendar.getInstance();
			aCalendar.add(Calendar.MONTH, -1);
			aCalendar.set(Calendar.DATE, 1);
			firstDateOfPreviousMonth = aCalendar.getTime();
			aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			lastDateOfPreviousMonth = aCalendar.getTime();
			sdf 			= new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
			startOfMonth	= sdf.format(firstDateOfPreviousMonth);
			endOfMonth		= sdf.format(lastDateOfPreviousMonth);
			valueObject		= new ValueObject();

			valueObject.put(DateTimeUtility.START_OF_MONTH, startOfMonth);
			valueObject.put(DateTimeUtility.END_OF_MONTH, endOfMonth);
			return valueObject;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			aCalendar					= null;
			firstDateOfPreviousMonth	= null;
			lastDateOfPreviousMonth		= null;
			startOfMonth				= null;
			endOfMonth					= null;
			valueObject					= null;
			sdf							= null;
		}
	}

	public static ValueObject getPreviousWeekStartAndEndDate() throws Exception {
		java.util.Date		date			= null;
		Calendar 			cal				= null;
		java.util.Date		start			= null;
		java.util.Date		end				= null;
		SimpleDateFormat	sdf				= null;
		String				startOfWeek		= null;
		String				endOfweek		= null;
		ValueObject			valueObject		= null;

		try {
			date = new java.util.Date();
			cal = Calendar.getInstance();
			cal.setTime(date);
			int i = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
			cal.add(Calendar.DATE, -i - 6);
			start = cal.getTime();
			cal.add(Calendar.DATE, 6);
			end = cal.getTime();
			sdf = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
			startOfWeek		= sdf.format(start);
			endOfweek		= sdf.format(end);
			valueObject		= new ValueObject();
			valueObject.put(DateTimeUtility.START_OF_WEEK, startOfWeek);
			valueObject.put(DateTimeUtility.END_OF_WEEK, endOfweek);
			return valueObject;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			date			= null;
			cal				= null;
			start			= null;
			end				= null;
			sdf				= null;
			startOfWeek		= null;
			endOfweek		= null;
			valueObject		= null;
		}
	}

	public static Timestamp getPreviousEndOfDateFromSelectedDate(String date) throws Exception {

		Calendar 	calendar 	= null;
		Timestamp 	timestamp2 	= null;

		try {

			calendar = Calendar.getInstance();
			calendar.setTime(getTimeStamp(date));

			calendar.add(Calendar.DATE, -1);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 998);	

			timestamp2 = new Timestamp(calendar.getTimeInMillis());

			return timestamp2;	
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			calendar 	= null ;
			timestamp2 	= null;
		}
	}

	public static Timestamp getNextEndOfDateFromSelectedDate(String date) throws Exception {

		Calendar 	calendar 	= null;
		Timestamp 	timestamp2 	= null;

		try {

			calendar = Calendar.getInstance();
			calendar.setTime(getTimeStamp(date));

			calendar.add(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 998);	

			timestamp2 = new Timestamp(calendar.getTimeInMillis());

			return timestamp2;	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			calendar 	= null ;
			timestamp2 	= null;
		}
	}

	public static String getMonthStringByDate(Timestamp	dateFormat) throws Exception {
		Calendar 			calender	= null;

		try {

			calender = Calendar.getInstance();
			calender.setTime(dateFormat);
			calender.get(Calendar.MONTH);

			return new SimpleDateFormat("MMMMM").format(dateFormat);			
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}

	public static String getMonthStringAbbrByDate(Timestamp	dateFormat) throws Exception {
		Calendar 			calender	= null;

		try {

			calender = Calendar.getInstance();
			calender.setTime(dateFormat);
			calender.get(Calendar.MONTH);

			return new SimpleDateFormat("MMM").format(dateFormat);			
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}

	public static int getDayOfMonthByDate(Timestamp dateFormat) throws Exception {
		Calendar 			calender	= null;

		try {

			calender = Calendar.getInstance();
			calender.setTime(dateFormat);
			calender.get(Calendar.MONTH);

			return Integer.parseInt(new SimpleDateFormat("MM").format(dateFormat));
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}

	public static int getYearFromDate(Timestamp dateFormat) throws Exception {
		Calendar 			calender	= null;

		try {

			calender = Calendar.getInstance();
			calender.setTime(dateFormat);

			return calender.get(Calendar.YEAR);
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}

	public static int getMonthFromDate(Timestamp dateFormat) throws Exception {
		Calendar 			calender	= null;

		try {

			calender = Calendar.getInstance();
			calender.setTime(dateFormat);

			return calender.get(Calendar.MONTH) + 1;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}

	public static ValueObject getCurrentDateTimeAsRange() throws Exception {

		ValueObject 		valueOutObject 	= null;
		SimpleDateFormat 	sdf 			= null;
		String 				currDate 		= null;
		Timestamp        	fromDate		= null;
		Timestamp        	toDate			= null;

		try {

			sdf 		= new SimpleDateFormat("dd-MM-yyyy");

			currDate 	= sdf.format(getCurrentTimeStamp());

			sdf               = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
			fromDate          = new Timestamp(sdf.parse(currDate + " 00:00:00:000").getTime());
			toDate            = new Timestamp(sdf.parse(currDate + " 23:59:59:998").getTime());

			valueOutObject = new ValueObject();

			valueOutObject.put("fromDate", fromDate);
			valueOutObject.put("toDate", toDate);

			return valueOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
			sdf 			= null;
			currDate 		= null;
			fromDate		= null;
			toDate			= null;
		}
	}

	public static Timestamp getNextMonthEndOfDateOnDays(int date, int month, int year) throws Exception {

		Calendar 	calendar 	= null;

		try {

			/* Date		- Monday date
			 * month	- Previous Month
			 * year		- Current Year
			 */
			calendar = Calendar.getInstance();
			calendar.set(year, month, date);

			calendar.add(Calendar.DATE, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 998);	

			return new Timestamp(calendar.getTimeInMillis());	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			calendar 	= null;
		}
	}

	public static Timestamp getTimeStampFromDateTimeString(String dateTimeString) {
		return Timestamp.valueOf(dateTimeString);
	}

	public static Timestamp getDateTimeFromStr(String date) throws Exception {

		SimpleDateFormat sdf = null;

		try {
			sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			return new Timestamp((sdf.parse(date)).getTime());
		} catch (Exception e) {
			
			return getCurrentTimeStamp();
		} finally {
			sdf = null;
		}
	}
	
	public static Timestamp getDateTimeFromStr(String date, String format) throws Exception {

		SimpleDateFormat sdf = null;

		try {
			sdf = new SimpleDateFormat(format);
			return new Timestamp((sdf.parse(date)).getTime());
		} catch (Exception e) {
			
			return getCurrentTimeStamp();
		} finally {
			sdf = null;
		}
	}
	
	

	public static int getPartOfDate(String partName, Date date) {

		if (partName == null || date == null) {
			return 0;
		}

		int ret = 0;

		java.util.List<String> filedsList = java.util.Arrays.asList(fieldsName);

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		switch (filedsList.indexOf(partName)) {
		case 0:
			ret = c.get(Calendar.YEAR);
			break;
		case 1:
			ret = c.get(Calendar.MONTH);
			break;
		case 2:
			ret = c.get(Calendar.HOUR);
			break;
		case 3:
			ret = c.get(Calendar.MINUTE);
			break;
		case 4:
			ret = c.get(Calendar.SECOND);
			break;
		case 5:
			ret = c.get(Calendar.DAY_OF_WEEK);
			break;
		case 6:
			ret = c.get(Calendar.DAY_OF_MONTH);
			break;
		case 7:
			ret = c.get(Calendar.DAY_OF_YEAR);
			break;
		case 8:
			// the ordinal number of current week in a month (it means a 'week' may be not contain 7 days)
			ret = c.get(Calendar.WEEK_OF_MONTH);
			break;
		case 9:
			// 1-7 correspond to 1, 8-14 correspond to 2,...
			ret = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
			break;
		case 10:
			ret = c.get(Calendar.WEEK_OF_YEAR);
			break;
		case 11:
			ret = (c.get(Calendar.ZONE_OFFSET)) / (1000 * 60 * 60);
			break;
		default:
			break;

		}
		return ret;
	}

	/**
	 * return an ISO formatted random date
	 *
	 *
	 * {talendTypes} Date
	 *
	 * {Category} TalendDate
	 *
	 * {param} string("2007-01-01") min : minimum date
	 *
	 * {param} string("2008-12-31") max : maximum date (superior to min)
	 *
	 * {example} getRandomDate("1981-01-18", "2005-07-24") {example} getRandomDate("1980-12-08", "2007-02-26")
	 */
	public static Date getRandomDate(String minDate, String maxDate) {
		if (minDate == null) {
			minDate = "1970-01-01";
		}

		if (maxDate == null) {
			maxDate = "2099-12-31";
		}

		if (!minDate.matches("\\d{4}-\\d{2}-\\d{2}") || !minDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
			throw new IllegalArgumentException("The parameter should be \"yyy-MM-dd\"");
		}

		int minYear 	= Integer.parseInt(minDate.substring(0, 4));
		int minMonth 	= Integer.parseInt(minDate.substring(5, 7));
		int minDay 		= Integer.parseInt(minDate.substring(8, 10));

		int maxYear 	= Integer.parseInt(maxDate.substring(0, 4));
		int maxMonth 	= Integer.parseInt(maxDate.substring(5, 7));
		int maxDay 		= Integer.parseInt(maxDate.substring(8, 10));

		Calendar minCal = Calendar.getInstance();
		minCal.set(Calendar.YEAR, minYear);
		minCal.set(Calendar.MONTH, minMonth - 1);
		minCal.set(Calendar.DAY_OF_MONTH, minDay);

		Calendar maxCal = Calendar.getInstance();
		maxCal.set(Calendar.YEAR, maxYear);
		maxCal.set(Calendar.MONTH, maxMonth - 1);
		maxCal.set(Calendar.DAY_OF_MONTH, maxDay);

		long random 	= minCal.getTimeInMillis()
				+ (long) ((maxCal.getTimeInMillis() - minCal.getTimeInMillis() + 1) * Math.random());
		return new Date(random);
	}

	/**
	 * get last day of the month
	 *
	 * @param date (a date value)
	 * @return a new date (the date has been changed to the last day)
	 *
	 * {talendTypes} Date
	 *
	 * {Category} TalendDate
	 *
	 * {param} date(mydate) date : the date to get last date of current month
	 *
	 * {example} getFirstDayMonth(2008/02/24 12:15:25) return 2008/02/28 12:15:25
	 */
	public static Date getLastDayOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DATE, lastDay);
		java.util.Date utilDate = c.getTime();
		 java.sql.Date sDate = new java.sql.Date(utilDate.getTime());
		return sDate;
	}

	/**
	 * get first day of the month
	 *
	 * @param date (a date value)
	 * @return a new date (the date has been changed to the first day)
	 *
	 * {talendTypes} Date
	 *
	 * {Category} TalendDate
	 *
	 * {param} date(mydate) date : the date to get first date of current month
	 *
	 * {example} getFirstDayMonth(2008/02/24 12:15:25) return 2008/02/01 12:15:25 #
	 */
	public static Date getFirstDayOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		java.util.Date utilDate = c.getTime();
		 java.sql.Date sDate = new java.sql.Date(utilDate.getTime());
		return sDate;
	}

	/**
	 * return difference between two dates
	 *
	 * @param Date1 ( first date )
	 * @param Date1 ( second date )
	 * @return a number of years, months, days ... date1 - date2
	 *
	 * {talendTypes} Long
	 *
	 * {Category} TalendDate
	 *
	 * {param} date(myDate) date1 : the first date to compare
	 *
	 * {param} date(myDate) date2 : the second date to compare
	 *
	 * {examples} diffDate(2008/11/24 12:15:25, 2008/10/14 16:10:35) : return 41 #
	 */

	public static long diffDate(Date date1, Date date2) {
		return diffDate(date1, date2, "dd", true);
	}

	/**
	 * return difference between two dates
	 *
	 * @param Date1 ( first date )
	 * @param Date1 ( second date )
	 * @param dateType value=("yyyy","MM","dd","HH","mm","ss","SSS") for type of return
	 * @param ignoreDST
	 * @return a number of years, months, days ... date1 - date2
	 *
	 * {talendTypes} Long
	 *
	 * {Category} TalendDate
	 *
	 * {param} date(myDate) date1 : the first date to compare
	 *
	 * {param} date(myDate2) date2 : the second date to compare
	 *
	 * {param} String("MM") dateType : the difference on the specified part
	 *
	 * {param} boolean(true) ignoreDST : ignore daylight saving time or not.
	 *
	 * {examples}
	 *
	 * ->> diffDate(2012/03/26 00:00:00, 2012/03/24 00:00:00, "dd", true) : return 2 not 1 in GMT+1#
	 *
	 * ->> diffDate(2012/03/26 00:00:00, 2012/03/24 00:00:00, "dd", false) : return 1 not 2 in GMT+1#
	 */

	public static long diffDate(Date date1, Date date2, String dateType, boolean ignoreDST) {

		if (date1 == null) {
			date1 = new Date(0);
		}
		if (date2 == null) {
			date2 = new Date(0);
		}

		if (dateType == null) {
			dateType = "SSS";
		}

		// ignore DST
		int addDSTSavings = 0;
		if (ignoreDST) {
			boolean d1In = TimeZone.getDefault().inDaylightTime(date1);
			boolean d2In = TimeZone.getDefault().inDaylightTime(date2);
			if (d1In != d2In) {
				if (d1In) {
					addDSTSavings = TimeZone.getDefault().getDSTSavings();
				} else if (d2In) {
					addDSTSavings = -TimeZone.getDefault().getDSTSavings();
				}
			}
		}

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);

		if (dateType.equalsIgnoreCase("yyyy")) { //$NON-NLS-1$
			return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
		} else if (dateType.equals("MM")) { //$NON-NLS-1$
			return (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 12 + (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
		} else {
			long diffTime = date1.getTime() - date2.getTime() + addDSTSavings;

			if (dateType.equalsIgnoreCase("HH")) { //$NON-NLS-1$
				return diffTime / (1000 * 60 * 60);
			} else if (dateType.equals("mm")) { //$NON-NLS-1$
				return diffTime / (1000 * 60);
			} else if (dateType.equalsIgnoreCase("ss")) { //$NON-NLS-1$
				return diffTime / 1000;
			} else if (dateType.equalsIgnoreCase("SSS")) { //$NON-NLS-1$
				return diffTime;
			} else if (dateType.equalsIgnoreCase("dd")) {
				return diffTime / (1000 * 60 * 60 * 24);
			} else {
				throw new RuntimeException("Can't support the dateType: " + dateType);
			}
		}
	}

	/**
	 * return difference between two dates ignore DST
	 *
	 * @param Date1 ( first date )
	 * @param Date1 ( second date )
	 * @param dateType value=("yyyy","MM","dd","HH","mm","ss","SSS") for type of return
	 * @return a number of years, months, days ... date1 - date2
	 *
	 * {talendTypes} Long
	 *
	 * {Category} TalendDate
	 *
	 * {param} date(myDate) date1 : the first date to compare
	 *
	 * {param} date(myDate2) date2 : the second date to compare
	 *
	 * {param} String("MM") dateType : the difference on the specified part
	 *
	 * {examples}
	 *
	 * ->> diffDate(2012/03/26 00:00:00, 2012/03/24 00:00:00, "dd") : return 2 not 1 in GMT+1#
	 */
	public static long diffDateIgnoreDST(Date date1, Date date2, String dateType) {
		return diffDate(date1, date2, dateType, true);
	}

	/**
	 * return difference between two dates by floor
	 *
	 * @param Date1 ( first date )
	 * @param Date1 ( second date )
	 * @param dateType value=("yyyy","MM") for type of return
	 * @return a number of years, months (date1 - date2)
	 *
	 * {talendTypes} Integer
	 *
	 * {Category} TalendDate
	 *
	 * {param} date(myDate) date1 : the first date to compare
	 *
	 * {param} date(myDate2) date2 : the second date to compare
	 *
	 * {param} String("MM") dateType : the difference on the specified part
	 *
	 * {examples}
	 *
	 * ->> diffDate(2009/05/10, 2008/10/15, "yyyy") : return 0
	 *
	 * ->> diffDate(2009/05/10, 2008/10/15, "MM") : return 6
	 */
	public static int diffDateFloor(Date date1, Date date2, String dateType) {
		if (date1 == null) {
			date1 = new Date(0);
		}
		if (date2 == null) {
			date2 = new Date(0);
		}

		if (dateType == null) {
			dateType = "yyyy";
		}

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);

		int result = 0;
		Calendar tmp = null;
		boolean flag = false;
		if (c1.compareTo(c2) < 0) {
			flag = true;
			tmp = c1;
			c1 = c2;
			c2 = tmp;
		}
		result = (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 12 + (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
		c2.add(Calendar.MONTH, result);
		result += c2.after(c1) ? -1 : 0;
		if (flag) {
			result = -result;
		}

		if (dateType.equalsIgnoreCase("yyyy")) {
			return result / 12;
		} else if (dateType.equals("MM")) {
			return result;
		} else {
			throw new RuntimeException("Can't support the dateType: " + dateType + " ,please try \"yyyy\" or \"MM\"");
		}
	}

	/**
	 * add number of day, month ... to a date (with Date given in String with a pattern)
	 *
	 * @param date (a Date given in string)
	 * @param pattern (the pattern for the related date)
	 * @param nb (the value to add)
	 * @param dateType (date pattern = ("yyyy","MM","dd","HH","mm","ss","SSS" ))
	 * @return a new date
	 *
	 * {talendTypes} String
	 *
	 * {Category} TalendDate
	 *
	 * {param} String("") string : date represent in string
	 *
	 * {param} String("yyyy-MM-dd") pattern : date pattern
	 *
	 * {param} int(addValue) nb : the added value
	 *
	 * {param} date("MM") dateType : the part to add
	 *
	 * {examples}
	 *
	 * ->> addDate("2008/11/24 12:15:25", "yyyy-MM-dd HH:mm:ss", 5,"dd") return "2008/11/29 12:15:25"
	 *
	 * ->> addDate("2008/11/24 12:15:25", "yyyy/MM/DD HH:MM:SS", 5,"ss") return "2008/11/24 12:15:30" #
	 *
	 */
	public static String addDate(String string, String pattern, int nb, String dateType) {
		if (string == null || dateType == null) {
			return null;
		}
		java.util.Date date = null;

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(pattern);
		try {
			date = sdf.parse(string);
		} catch (ParseException e) {
			throw new RuntimeException(pattern + " can't support the date!"); //$NON-NLS-1$
		}
		String dateString = sdf.format(addDate((Date) date, nb, dateType));

		return dateString;
	}

	/**
	 * add number of day, month ... to a date (with Java date type !)
	 *
	 * @param date (a <code>Date</code> type value)
	 * @param nb (the value to add)
	 * @param dateType (date pattern = ("yyyy","MM","dd","HH","mm","ss","SSS" ))
	 * @return a new date
	 *
	 * {talendTypes} Date
	 *
	 * {Category} TalendDate
	 *
	 * {param} date(myDate) date : the date to update
	 *
	 * {param} int(addValue) nb : the added value
	 *
	 * {param} date("MM") dateType : the part to add
	 *
	 * {examples}
	 *
	 * ->> addDate(dateVariable), 5,"dd") return a date with 2008/11/29 12:15:25 (with dateVariable is a date with
	 * 2008/11/24 12:15:25) #
	 *
	 * ->> addDate(2008/11/24 12:15:25, 5,"ss") return 2008/11/24 12:15:30 #
	 *
	 */
	public static Date addDate(Date date, int nb, String dateType) {
		if (date == null || dateType == null) {
			return null;
		}
		 java.util.Date toDate = new java.util.Date(date.getTime());
		Calendar c1 = Calendar.getInstance();
		c1.setTime(toDate);

		if (dateType.equalsIgnoreCase("yyyy")) { //$NON-NLS-1$
			c1.add(Calendar.YEAR, nb);
		} else if (dateType.equals("MM")) { //$NON-NLS-1$
			c1.add(Calendar.MONTH, nb);
		} else if (dateType.equalsIgnoreCase("dd")) { //$NON-NLS-1$
			c1.add(Calendar.DAY_OF_MONTH, nb);
		} else if (dateType.equals("HH")) { //$NON-NLS-1$
			c1.add(Calendar.HOUR, nb);
		} else if (dateType.equals("mm")) { //$NON-NLS-1$
			c1.add(Calendar.MINUTE, nb);
		} else if (dateType.equalsIgnoreCase("ss")) { //$NON-NLS-1$
			c1.add(Calendar.SECOND, nb);
		} else if (dateType.equalsIgnoreCase("SSS")) { //$NON-NLS-1$
			c1.add(Calendar.MILLISECOND, nb);
		} else {
			throw new RuntimeException("Can't support the dateType: " + dateType);
		}

		return (Date) c1.getTime();
	}

	public static int getDiffBetweenTwoMonth(Timestamp endTimeStamp ,Timestamp StartTimeStamp){
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(StartTimeStamp);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endTimeStamp);

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

		return diffMonth;
	}

	public static long getHourDiffBetweenTwoTime(Timestamp startTimeStamp, Timestamp endTimeStamp) {

		long diffInSec = endTimeStamp.getTime() - startTimeStamp.getTime();

		return diffInSec / (60 * 60 * 1000);
	}

	public static ArrayList<String> getMonthList(LocalDateTime startDate, LocalDateTime endDate) {
		ArrayList<String>		monthList		= null;
		long					monthDiff		= 0;

		try {

			monthList	= new ArrayList<String>();
			monthDiff	= getMonthDifference(startDate, endDate);

			for (int i = 0; i < monthDiff; i++) {
				monthList.add(getMonthTable(startDate.plusMonths(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return monthList;
	}

	public static String getMonthTable(LocalDateTime date) {
		return (date.getYear() + "_" + date.getMonthValue()).toString();
	}

	public static String getDateAfterNoOfDays(int noOfDays) throws Exception {
		String futureDate		= null;

		try {
			Calendar now = Calendar.getInstance();

			now.add(Calendar.DATE, noOfDays);

			futureDate	= now.get(Calendar.DATE) + " " + getMonthName(now.get(Calendar.MONTH)) + " " + now.get(Calendar.YEAR);

			return futureDate;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			futureDate		= null;
		}
	}
	
	public static Date getDateAfterNoOfDays1(Date date,int noOfDays) throws Exception {
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(date);
			now.add(Calendar.DATE, noOfDays);
			
			java.util.Date utilDate = now.getTime();
			java.sql.Date sDate = new java.sql.Date(utilDate.getTime());
			return sDate;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
	}

	public static String getMonthName(int monthIndex) {
		return new DateFormatSymbols().getShortMonths()[monthIndex].toString();
	}

	public static boolean checkTimeExistBetweenTimeSlot(long currentTimeInMiliSeconds) throws Exception {
		long					fromCurrentDateTime			= 0;
		long					toCurrentDateTime			= 0;
		String					currentDate					= null;
		SimpleDateFormat		sdf							= null;
		boolean					backDate					= false;

		try {
			sdf 					= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.sss");
			currentDate				= DateTimeUtility.getCurrentDateString();
			fromCurrentDateTime		= (sdf.parse(currentDate + " 00:00:00.0").getTime());
			toCurrentDateTime		= (sdf.parse(currentDate + " 05:59:59.998").getTime());

			if(currentTimeInMiliSeconds > 0) {
				if(currentTimeInMiliSeconds > fromCurrentDateTime && currentTimeInMiliSeconds < toCurrentDateTime) {
					backDate	= true;
				} else {
					backDate	= false;
				}
			}
			return	backDate;
		}catch (Exception e) {
			// TODO: handle exception
			throw	e;
		}finally {
			fromCurrentDateTime		= 0;
			toCurrentDateTime		= 0;
			currentDate				= null;
			sdf						= null;
			backDate				= false;
		}
	}

	public static Timestamp getMinimumTimeStamp(Timestamp t1, Timestamp t2) throws Exception {
		try {
			if(t1.getTime() < t2.getTime()) {
				return t1;
			}

			return t2;
		} catch (Exception e) {
			// TODO: handle exception
			throw	e;
		} finally {

		}
	}

	public static long getTimeFromDate(Timestamp date) throws Exception {
		try {
			String				sDate1	= getDateFromTimeStamp(date);
			java.util.Date 	date1	= new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);  

			return date1.getTime();
		} catch (Exception e) {
			// TODO: handle exception
			throw	e;
		} finally {

		}
	}

	public static Timestamp convertDateFormatToOtherDateFormat(String date, String fromFormat, String toFormat) throws Exception {
		SimpleDateFormat	fromDateFormat		= null;
		SimpleDateFormat	toDateFormat		= null;

		try {

			if(fromFormat.contains("/") && toFormat.contains("-")) {
				fromDateFormat			= new SimpleDateFormat(fromFormat);
				toDateFormat			= new SimpleDateFormat(toFormat);
				return appendTimeToDate(toDateFormat.format(new Timestamp(fromDateFormat.parse(date).getTime())));
			}

			return null;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		}
	}
	
	public static ValueObject getDayDiffBetweenTwoDatesInHoursAndMinutes(Timestamp startDate, Timestamp endDate) throws Exception {

		ValueObject		valueObject			= null;
		String 			diffHoursStr		= null;
		String 			diffMinutesStr		= null;
		String 			timeDifference		= null;

		try {
			
			long 		diff 				= endDate.getTime() - startDate.getTime();
			long 		diffHours 			= diff / (60 * 60 * 1000);
			long 		diffMinutes 		= diff / (60 * 1000) % 60;

			diffHoursStr	= diffHours+":";
			diffMinutesStr	= diffMinutes +"";
			timeDifference 	= diffHoursStr.concat(diffMinutesStr);

			valueObject	= new ValueObject();
			valueObject.put("timeDifference", timeDifference);
			valueObject.put("diffHours", diffHours);

			return valueObject;
			
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			valueObject		= null;
			diffHoursStr	= null;
			diffMinutesStr	= null;
			timeDifference	= null;
		}
	}
}