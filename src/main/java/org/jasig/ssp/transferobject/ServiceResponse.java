package org.jasig.ssp.transferobject;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.google.common.collect.Lists;

public class ServiceResponse {

	private final transient boolean success;

	private final transient String message;

	public ServiceResponse(final boolean success) {
		this.success = success;
		message = "";
	}

	public ServiceResponse(final boolean success, final String message) {
		this.success = success;
		this.message = message;
	}

	/**
	 * Extract the Validation messages out of the
	 * MethodArgumentNotValidException, and use as the ServiceResponse Message.
	 * 
	 * @param success
	 *            If the response should indicate success or not
	 * @param e
	 *            Error messages to show
	 */
	public ServiceResponse(final boolean success,
			final MethodArgumentNotValidException e) {
		this.success = success;

		StringBuilder sb;

		// collect the error messages
		final List<String> errorMessages = Lists.newArrayList();
		for (ObjectError error : e.getBindingResult().getAllErrors()) {
			sb = new StringBuilder();

			// get the field name if it is a field error.
			if (error instanceof FieldError) {
				final FieldError fe = (FieldError) error;
				sb.append("[").append(fe.getField());
			} else {
				sb.append("[");
			}

			// get the default message
			sb.append(" ").append(error.getDefaultMessage()).append("] ");
			// add it to the list of error messages
			errorMessages.add(sb.toString());
		}

		// sort the messages for readablility
		Collections.sort(errorMessages);

		// introduce the error messages
		final int errorCount = e.getBindingResult().getErrorCount();
		sb = new StringBuilder("Validation failed for argument ")
				.append(e.getParameter().getParameterName()).append(", with ")
				.append(errorCount)
				.append(errorCount > 1 ? " errors: " : " error: ");

		// append the sorted error messages to the introduction and set as the
		// service response message.
		sb.append(StringUtils.join(errorMessages, ","));

		message = sb.toString();
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
