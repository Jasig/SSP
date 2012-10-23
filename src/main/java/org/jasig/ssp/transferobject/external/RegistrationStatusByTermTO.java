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
package org.jasig.ssp.transferobject.external;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.jasig.ssp.model.external.RegistrationStatusByTerm;

import com.google.common.collect.Lists;

public class RegistrationStatusByTermTO implements
		ExternalDataTO<RegistrationStatusByTerm>, Serializable {

	private static final long serialVersionUID = -2247016612567563155L;

	private String schoolId, termCode;

	private int registeredCourseCount;

	public RegistrationStatusByTermTO() {
		super();
	}

	public RegistrationStatusByTermTO(final RegistrationStatusByTerm model) {
		super();
		from(model);
	}

	@Override
	public final void from(final RegistrationStatusByTerm model) {
		termCode = model.getTermCode();
		schoolId = model.getSchoolId();
		registeredCourseCount = model.getRegisteredCourseCount();
	}

	public static List<RegistrationStatusByTermTO> toTOList(
			final Collection<RegistrationStatusByTerm> models) {
		final List<RegistrationStatusByTermTO> tObjects = Lists.newArrayList();
		for (final RegistrationStatusByTerm model : models) {
			tObjects.add(new RegistrationStatusByTermTO(model)); // NOPMD by
																	// jon.adams
		}

		return tObjects;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(final String termCode) {
		this.termCode = termCode;
	}

	public int getRegisteredCourseCount() {
		return registeredCourseCount;
	}

	public void setRegisteredCourseCount(final int registeredCourseCount) {
		this.registeredCourseCount = registeredCourseCount;
	}
}
