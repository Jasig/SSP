/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jasig.ssp.dao.ObjectExistsException;
import org.jasig.ssp.dao.PersonExistsException;
import org.jasig.ssp.transferobject.jsonserializer.BooleanPrimitiveToStringSerializer;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

// NON_NULL preserves backward compatibility with any clients which might
// not be prepared to accept the "detail" field
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServiceResponse implements Serializable {

	private static final long serialVersionUID = 256473140649271859L;

	// Prior to adding the "detail" field, JSON output (from toString())
	// was hand-rolled and the success field value was wrapped in quotes, so
	// use a custom serializer to preserve that behavior.
	@JsonSerialize(using = BooleanPrimitiveToStringSerializer.class)
	private boolean success = false;

	private String message = "";

	// would rather a generic map of Serializable->Serializable, but just
	// too difficult to get that to work properly w/ Jackson deserialization
	// (current tests require all TO objects to be Jackson de/serializable)
	Map<String, Map<String,String>> detail;

	public ServiceResponse() {
		super();
	}

	public ServiceResponse(final boolean success) {
		this.success = success;
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

		// collect the error messages
		final List<String> errorMessages = Lists.newArrayList();
		for (final ObjectError error : e.getBindingResult().getAllErrors()) {
			final StringBuilder sb = new StringBuilder(); // NOPMD

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
		final StringBuilder sb = new StringBuilder(
				"Validation failed for argument ")
				.append(e.getParameter().getParameterName()).append(", with ")
				.append(errorCount)
				.append(errorCount > 1 ? " errors: " : " error: ");

		// append the sorted error messages to the introduction and set as the
		// service response message.
		sb.append(StringUtils.join(errorMessages, ","));

		message = sb.toString();
	}

	/**
	 * Uses the message from the given {@link ObjectExistsException} and adds
	 * its custom fields to the <code>detail</code> collection.
	 *
	 * @param success
	 *            If the response should indicate success or not
	 * @param e
	 *            Unexpected object existence event to describe
	 */
	@SuppressWarnings("unchecked")
	public ServiceResponse(final boolean success,
						   final ObjectExistsException e) {
		this(success, e.getMessage());
		final Map<String,Map<String,String>> detail =
				new HashMap<String,Map<String, String>>();
		detail.put("typeInfo", new HashMap<String,String>() {{
			put("name", e.getName());
		}});
		detail.put("lookupFields", toStringMap(e.getLookupFields()));
		this.detail = detail;
	}
	/**
	 * Uses the message from the given {@link PersonExistsException} and adds
	 * its custom fields to the <code>detail</code> collection.
	 *
	 * @param success
	 *            If the response should indicate success or not
	 * @param e
	 *            Unexpected object existence event to describe
	 */
	public ServiceResponse(final boolean success,
						   final PersonExistsException e) {
		this(success, e.getMessage());
		final Map<String,Map<String,String>> detail =
				new HashMap<String,Map<String, String>>();
		detail.put("typeInfo", new HashMap<String,String>() {{
			put("name", e.getName());
		}});
		detail.put("details", new HashMap<String,String>() {{
			put("error",e.getError());
			put("conflictingId", e.getConflictingId() != null ? e.getConflictingId().toString() : null);
			put("conflictingUsername", e.getConflictingUsername());
			put("conflictingSchoolId", e.getConflictingSchoolId());
			put("originalUsername", e.getOriginalUsername());
			put("originalSchoolId", e.getOriginalSchoolId());

		}});
		this.detail = detail;
	}
	private Map<String,String> toStringMap(Map<String, ? extends Serializable> from) {
		if ( from == null ) {
			return null;
		}
		final Map<String,String> strings =Maps.newHashMapWithExpectedSize(from.size());
		for ( Map.Entry<String, ? extends Serializable> entry : from.entrySet() ) {
			final String value =  entry.getValue() == null ? null : entry.getValue().toString();
			strings.put(entry.getKey(),value);
		}
		return strings;
	}

	public boolean isSuccess() {
		return success;
	}

	protected void setSuccess(final boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	protected void setMessage(final String message) {
		this.message = message;
	}

	public Map<String, Map<String,String>> getDetail() {
		return detail;
	}

	protected void setDetail(final Map<String, Map<String,String>> detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch ( RuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new RuntimeException(e);
		}
	}
}