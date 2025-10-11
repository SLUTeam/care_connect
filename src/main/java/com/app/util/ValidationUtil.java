package com.app.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

	private ValidationUtil() {
		
    }

    public static boolean isNullOrEmpty(String value) {
        return null == value || value.trim().isEmpty();
    }
 

    public static boolean isNull(UUID value) {
        return null == value;
    }

 

    public static boolean isNull(Double value) {
        return null == value || value == 0;
    }

 

    public static boolean isNull(Integer value) {
        return null == value || value == 0;
    }

 

    public static boolean isNullObject(Object value) {
        return null == value;
    }

 

    public static boolean isNull(byte[] value) {
        return null == value || value.toString().trim().isEmpty();
    }

 

    public static boolean isValidPinCode(Long value) {
        String regex = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$";
        Pattern p = Pattern.compile(regex);
        String s = String.valueOf(value);
        Matcher m = p.matcher(s);
        return m.matches();

 

    }

 

    public static boolean isValidMobileNumber(String value) {
        String regex = "(?:\\s+|)((0|(?:(\\+|)91))(?:\\s|-)*(?:(?:\\d(?:\\s|-)*\\d{9})|(?:\\d{2}(?:\\s|-)*\\d{8})|(?:\\d{3}(?:\\s|-)*\\d{7}))|\\d{10})(?:\\s+|)";
        Pattern p = Pattern.compile(regex);
        String s = String.valueOf(value);
        Matcher m = p.matcher(s);
        return m.matches();
    }

 

    public static boolean isValidAccountNumber(String value) {
        String regex = "^\\d{9,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }

 

    public static boolean isValidPanNumber(String value) {
        String regex = "^[a-zA-Z]{3}[PCHFATBLJG]{1}[a-zA-Z]{1}[0-9]{4}[a-zA-Z]{1}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }

 

    public static boolean isValidAadhaarNumber(String value) {
        String regex = "^(\\d{12}|\\d{16})$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }

 

    /*
     * public static boolean isValidVoterId(String value) { String regex =
     * "^([a-zA-Z]){3}([0-9]){7}?$"; Pattern p = Pattern.compile(regex); Matcher m =
     * p.matcher(value); return m.matches(); }
     */

 

    public static boolean isValidPassportNumber(String value) {
        String regex = "^(?!^0+$)[a-zA-Z0-9]{3,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }

 

    public static boolean isValidEmailId(String value) {
        String regex = "^(?=.{1,64}@)[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9-.]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }

 

    public static boolean isValidName(String value) {
        String regex = "^(?=.{1,40}$)[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }

 

    public static boolean isValidAddress(String value) {
        String regex = "^(\\w*\\s*[\\:\\#\\-\\,\\/\\.\\(\\)\\&]*)+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }
    
    
    /**
     * Return true if the supplied value is not matched with given pattern.
     *
     * @param value
     * @return
     */
    public static boolean isValidRemark(String value) {
        String regex1 = "^[-@.\\/#&+\\w\\s\\(\\)\\,]*$";
        String regex2 = "^[0-9]*$";
        String regex3 = "^[@||#]*$";
        if (isNullOrEmpty(value)) {
            return false;
        }
        if (!((Pattern.compile(regex1).matcher(value).matches())
                && (!(Pattern.compile(regex2).matcher(value).matches())))
                || (Pattern.compile(regex3).matcher(value).matches())) {
            return true;
        }
        return false;
    }

 

    /**
     * Return true if the supplied values is negative. Otherwise, return false.
     * 
     * @param values
     * @return boolean
     */
    public static boolean isValueNegative(Number values) {
        if (values instanceof Double) {
            Double value = (Double) values;
            if (value.doubleValue() < 0) {
                return true;
            }
        }
        if (values instanceof Integer) {
            Integer value = (Integer) values;
            if (value.intValue() < 0) {
                return true;
            }
        }
        return false;
    }

 

    // Added trim function for NotnullorEmpty Elements
    public static String getFormattedString(String value) {
        if (value != null) {
            return value.trim();
        }
        return value;
    }
    
    /**
     * 
     * return true if the given value are not matched with the Pattern.
     * 
     * @param value
     * @return
     */
    public static boolean validAlphaNumerics(String value) {
        String regex1 = "[0-9a-zA-z\\s]*";
        String regex2 = "^[0-9]+$";
        if (isNullOrEmpty(value)) {
            return false;
        }
        return (!(Pattern.compile(regex1).matcher(value).matches())
                || (Pattern.compile(regex2).matcher(value).matches()));
    }

 

    public static boolean validAlphabets(String value) {
        String regex = "[a-zA-z\\s]*";
        if (isNullOrEmpty(value)) {
            return false;
        }
        return !(Pattern.compile(regex).matcher(value).matches());
    }

 

    public static boolean validAlphaNumericsNumbers(String value) {
        String regex1 = "^[a-zA-Z0-9]+$";
        String regex2 = "^[a-zA-Z]+$";

 

        if (isNullOrEmpty(value)) {
            return false;
        }
        return (!Pattern.compile(regex1).matcher(value).matches() || Pattern.compile(regex2).matcher(value).matches());
    }

 

    public static boolean validRollNumber(String value) {
        String regex1 = "\\b\\w{1,30}\\b";
        if (Pattern.compile(regex1).matcher(value).matches()) {
            String regex2 = "[a-zA-Z\\s]*";
            Pattern p = Pattern.compile(regex2);
            Matcher m = p.matcher(value);
            return m.matches();
        }
        if (Pattern.compile(regex1).matcher(value).matches()) {
            String regex = "[0-9a-zA-Z\\s]*";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(value);
            return !m.matches();
        } 
        else
        {
            return true;
        }

 

    }

 

    public static boolean isValidCharacters(String value) {
        String regex1 = "^[-@.\\/#&+\\w\\s\\(\\)\\_\\-]*$";
        String regex2 = "^[0-9]*$";
        String regex3 = "^[@||#]*$";
        if (isNullOrEmpty(value)) {
            return false;
        }
        if (!((Pattern.compile(regex1).matcher(value).matches())
                && (!(Pattern.compile(regex2).matcher(value).matches())))
                || (Pattern.compile(regex3).matcher(value).matches())) {
            return true;
        }
        return false;
    }

 

    public static boolean isValidAccountHolderName(String value) {
        String regex1 = "[0-9a-zA-z\\s\\-\\@\\,\\(\\)\\&\\/]*";
        String regex2 = "^[^@]*@?[^@]*$";
        String regex3 = "^[.||/||-||_||@||#||$||*||^||!||%||,||(||)]*$";
        String regex4 = "^[0-9]*$";

 

        if (isNullOrEmpty(value)) {
            return false;
        }
        if (!((Pattern.compile(regex1).matcher(value).matches())
                && ((Pattern.compile(regex2).matcher(value).matches())))
                || (Pattern.compile(regex3).matcher(value).matches())
                || (Pattern.compile(regex4).matcher(value).matches())) {
            return true;
        }
        return false;
    }

 

    public static boolean validAlphabetsAndCharacters(String value) {
        String regex = "[a-zA-z\\s\\-\\(\\)\\[\\]]*";
        if (isNullOrEmpty(value)) {
            return false;
        }
        return !(Pattern.compile(regex).matcher(value).matches());
    }

 

    public static boolean validRollNumberPrefix(String value) {
        String regex = "[0-9a-zA-z\\s\\-\\/]*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }

 

    public static boolean validationPassportNumber(String value) {
        String regex = "\\b\\w{5,20}\\b";
        if (Pattern.compile(regex).matcher(value).matches()) {
            String regex1 = "[0-9A-Z\\s]*";
            Pattern p = Pattern.compile(regex1);
            Matcher m = p.matcher(value);
            return !m.matches();
        } else {
            return true;
        }

 

    }

	public static boolean isValidPhoneNo(String phoneNo) {
		if (phoneNo.length() < 10 || phoneNo.length() > 10) {
			return true;
		}
		return false;
	}

  
}
