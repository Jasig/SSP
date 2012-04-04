package edu.sinclair.mygps.util;

import java.util.UUID;

public class MyGpsStringUtils {

	public static String stringFromYear(int year){
		if(0==year){
			return "";
		}else{
			return String.valueOf(year);
		}
	}
	
	public static String stringFromBoolean(boolean val){
		if(val){
			return "true";
		}else{
			return "";
		}
	}
	
	public static boolean booleanFromString(String val){
		if(val.equalsIgnoreCase("true")){
			return true;
		}else{
			return false;
		}
	}
	
	public static UUID uuidFromColdfusionString(String uuid){
		String fixDashes = uuid.substring(0,23) + "-" + uuid.substring(23,35);
		return UUID.fromString(fixDashes);
	}

	public static String coldfusionStringFromUUID(UUID uuid){
		String uuidString = uuid.toString().toUpperCase();
		return uuidString.substring(0,23) + uuidString.substring(24,36);
	}
	
	public static int compareStandardUuidAndColdfusionString(UUID a, String b){
		return a.compareTo(uuidFromColdfusionString(b));
	}
}
