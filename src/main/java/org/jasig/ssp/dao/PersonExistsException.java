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
package org.jasig.ssp.dao;

import java.util.UUID;

public class PersonExistsException extends RuntimeException {

	private static final long serialVersionUID = 6996731597125579874L;
	
	public static final String ERROR_USERNAME_SCHOOLID_MISMATCH = "USERNAME_SCHOOLID_MISMATCH";
	
	public static final String ERROR_USERNAME_EXISTING = "ERROR_USERNAME_EXISTING";

	public static final String ERROR_SCHOOL_ID_EXISTING = "ERROR_SCHOOL_ID_EXISTING";

	public static final String ERROR_CONSTRAINT_VIOLATION_SCHOOL_ID = "ERROR_CONSTRAINT_VIOLATION_SCHOOL_ID";

	public static final String ERROR_CONSTRAINT_VIOLATION_USERNAME = "ERROR_CONSTRAINT_VIOLATION_USERNAME";


	public PersonExistsException(String error,
			UUID conflictingId, String conflictingUsername,
			String conflictingSchoolId, 
			String originalUsername, String originalSchoolId, String name) {
		super();
		this.error = error;
		this.conflictingId = conflictingId;
		this.conflictingUsername = conflictingUsername;
		this.conflictingSchoolId = conflictingSchoolId;
		this.originalUsername = originalUsername;
		this.originalSchoolId = originalSchoolId;
		this.name = name;
	}
	
	public PersonExistsException(String error,
			UUID conflictingId, String conflictingUsername,
			String conflictingSchoolId, 
			String originalUsername, String originalSchoolId, String name,Throwable cause) {
		super(cause);
		this.error = error;
		this.conflictingId = conflictingId;
		this.conflictingUsername = conflictingUsername;
		this.conflictingSchoolId = conflictingSchoolId;
		this.originalUsername = originalUsername;
		this.originalSchoolId = originalSchoolId;
		this.name = name;
	}	

	private String error;
	
	private UUID conflictingId;
	
	private String conflictingUsername;
	
	private String conflictingSchoolId;
	
	
	private String originalUsername;
	
	private String originalSchoolId;


	/**
	 * Entity (class) name
	 */
	private String name;

	public PersonExistsException() {
		super();
	}

	public PersonExistsException(final String message) {
		super(message);
	}

	public PersonExistsException(final Throwable cause) {
		super(cause);
	}

	public PersonExistsException(final String message, final Throwable cause) {
		super(message, cause);
	}


	@SuppressWarnings("unused")
	private void setName(final String name) { // NOPMD for serialization
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public UUID getConflictingId() {
		return conflictingId;
	}

	public void setConflictingId(UUID conflictingId) {
		this.conflictingId = conflictingId;
	}

	public String getConflictingUsername() {
		return conflictingUsername;
	}

	public void setConflictingUsername(String conflictingUsername) {
		this.conflictingUsername = conflictingUsername;
	}

	public String getConflictingSchoolId() {
		return conflictingSchoolId;
	}

	public void setConflictingSchoolId(String conflictingSchoolId) {
		this.conflictingSchoolId = conflictingSchoolId;
	}

	public String getOriginalUsername() {
		return originalUsername;
	}

	public void setOriginalUsername(String originalUsername) {
		this.originalUsername = originalUsername;
	}

	public String getOriginalSchoolId() {
		return originalSchoolId;
	}

	public void setOriginalSchoolId(String originalSchoolId) {
		this.originalSchoolId = originalSchoolId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
