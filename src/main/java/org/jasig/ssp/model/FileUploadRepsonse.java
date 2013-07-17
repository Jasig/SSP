package org.jasig.ssp.model;
 
 
public class FileUploadRepsonse {
 
    private boolean success;
    
    private String errorMessage;
     
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
     
    public String toString(){
        return "{success:"+this.success+",message:'"+this.getErrorMessage()+"'}";
    }
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}