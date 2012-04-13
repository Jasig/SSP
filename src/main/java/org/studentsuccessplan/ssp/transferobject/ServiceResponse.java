package org.studentsuccessplan.ssp.transferobject;

public class ServiceResponse {

	private final transient boolean success;

	private final transient String message;

	public ServiceResponse(final boolean success) {
		this.success = success;
		this.message = "";
	}

	public ServiceResponse(final boolean success, final String message) {
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
		final StringBuilder sb = new StringBuilder();
		sb.append("{\"success\":\"");
		if (success) {
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
