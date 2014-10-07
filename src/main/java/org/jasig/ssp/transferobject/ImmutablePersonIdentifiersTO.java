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

import java.util.UUID;

/**
 * Encapsulates the two types of person identifiers we can rely on for stably (that's the 'immutable' part) identifying
 * a given {@code Person}, whether they're internal or external-only.
 *
 * <p>Not using Pair b/c the parameterized types on that make JSON deserialization very painful,
 * and this explicit POJO results in much more descriptive JSON field names anyway.</p>
 */
public class ImmutablePersonIdentifiersTO {

	private UUID id;
	private String schoolId;

	public ImmutablePersonIdentifiersTO() {
		super();
	}

	public ImmutablePersonIdentifiersTO(UUID id, String schoolId) {
		super();
		this.id = id;
		this.schoolId = schoolId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ImmutablePersonIdentifiersTO)) return false;

		ImmutablePersonIdentifiersTO that = (ImmutablePersonIdentifiersTO) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (schoolId != null ? !schoolId.equals(that.schoolId) : that.schoolId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (schoolId != null ? schoolId.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ImmutablePersonIdentifiersTO{" +
				"id=" + id +
				", schoolId='" + schoolId + '\'' +
				'}';
	}
}
