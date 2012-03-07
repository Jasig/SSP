package edu.sinclair.ssp.transferobject;

public class ServiceResponse {

	private boolean success;
	
	private String message;
	
	public ServiceResponse(boolean success){
		this.success = success;
	}
	
	public ServiceResponse(boolean success, String message){
		this.success = success;
		this.message = message;
	}
	
	public boolean isSuccess(){
		return success;
	}
	
	public String getMessage(){
		return message;
	}
}
