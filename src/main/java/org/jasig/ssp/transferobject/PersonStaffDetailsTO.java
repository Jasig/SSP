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
package org.jasig.ssp.transferobject; // NOPMD

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.PersonStaffDetails;

/**
 * PersonStaffDetails transfer object
 * 
 * @author jon.adams
 */
public class PersonStaffDetailsTO extends
		AbstractAuditableTO<PersonStaffDetails> implements
		TransferObject<PersonStaffDetails> {

	@NotNull
	private UUID personId;

	private String officeLocation;

	private String officeHours;

	private String departmentName;

	public PersonStaffDetailsTO() {
		super();
	}

	public PersonStaffDetailsTO(final PersonStaffDetails model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonStaffDetails model) {
		super.from(model);

		officeLocation = model.getOfficeLocation();
		officeHours = model.getOfficeHours();
		departmentName = model.getDepartmentName();
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(final String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public String getOfficeHours() {
		return officeHours;
	}

	public void setOfficeHours(final String officeHours) {
		this.officeHours = officeHours;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(final String departmentName) {
		this.departmentName = departmentName;
	}
}