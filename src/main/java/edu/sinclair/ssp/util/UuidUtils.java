package edu.sinclair.ssp.util;

import java.util.UUID;

public class UuidUtils {

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
	
	public static String uuidToString(UUID val){
		if(null!=val){
			return val.toString();
		}else{
			return "No UUID assigned";
		}
	}
	
}
