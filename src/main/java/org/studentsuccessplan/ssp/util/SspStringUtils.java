package edu.sinclair.ssp.util;


public class SspStringUtils {

	public static String stringFromYear(int year) {
		if (0 == year) {
			return "";
		} else {
			return String.valueOf(year);
		}
	}

	public static String stringFromBoolean(boolean val) {
		if (val) {
			return "true";
		} else {
			return "";
		}
	}

	public static boolean booleanFromString(String val) {
		if (val.equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

}
