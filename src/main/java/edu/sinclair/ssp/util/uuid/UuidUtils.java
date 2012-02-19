package edu.sinclair.ssp.util.uuid;

import java.util.UUID;

/**
 * Utilities for working with UUIDs
 * @author daniel
 *
 */
public class UuidUtils {

	/**
	 * Is the parameter a UUID?
	 */
	public static boolean isUUID(String str) {
		
		if (str == null)
			return false;
		else if (str.length() != 36)
			return false;
		else
			try{
				UUID.fromString(str);
			}catch(IllegalArgumentException e){
				return false;
			}
			return true;
	}
	
	/**
	 * Turn the parameter into a string
	 */
	public static String uuidToString(UUID val){
		if(null!=val){
			return val.toString();
		}else{
			return "No UUID assigned";
		}
	}
	
}
