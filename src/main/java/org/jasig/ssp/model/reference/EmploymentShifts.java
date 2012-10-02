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
package org.jasig.ssp.model.reference;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jasig.ssp.transferobject.jsonserializer.CodeAndProperty;
import org.jasig.ssp.transferobject.jsonserializer.CodeAndPropertySerializer;

/**
 * Employment Shifts
 */
@JsonSerialize(using = CodeAndPropertySerializer.class)
public enum EmploymentShifts implements CodeAndProperty {

	/**
	 * First shift ("1st")
	 */
	FIRST("1st"),

	/**
	 * Second shift ("2nd")
	 */
	SECOND("2nd"),

	/**
	 * Third shift ("3rd")
	 */
	THIRD("3rd"),

	/**
	 * Shift not applicable ("Not Applicable")
	 */
	NOT_APPLICABLE("Not Applicable");

	/**
	 * Gets the specified enum
	 * 
	 * @param value
	 *            the value
	 * @return the specified enum, or null if not found
	 */
	public static EmploymentShifts getEnumByValue(@NotNull final String value) {
		if ("1st".equals(value)) {
			return FIRST;
		} else if ("2nd".equals(value)) {
			return SECOND;
		} else if ("3rd".equals(value)) {
			return THIRD;
		} else if ("Not Applicable".equals(value)) {
			return NOT_APPLICABLE;
		}

		return null;
	}

	private String title;

	private EmploymentShifts(final String title) {
		this.title = title;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getCode() {
		return name();
	}
}