package com.app.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ConvertionUtil {
	
	 private static DecimalFormat df = new DecimalFormat("0.00");
	
	 
	public static String roundToTwoDecimalPlaces(Double value) {
		return String.format("%.2f", value);
	}
	
	public static double roundOffToUpper(Double value) {
		BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	public static double conversionRoundOff(Double value) {
		BigDecimal bd = new BigDecimal(value).setScale(4, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
 
	public static void main(String[] args) {
		
		Double invConv = ConvertionUtil.roundOffToUpper(1 / 3.67);
		
		System.out.println(invConv);
	}
}
