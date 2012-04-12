package org.studentsuccessplan.ssp.transferobject;

public class ServiceResponse {

	private boolean success;

	private String message;

	public ServiceResponse(boolean success) {
		this.success = success;
	}

	public ServiceResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"success\":\"");
		if (success == true) {
			sb.append("true");
		} else {
			sb.append("false");
		}
		sb.append("\", \"message\":\"");
		sb.append(message);
		sb.append("\"}");
		return sb.toString();
	}
}
