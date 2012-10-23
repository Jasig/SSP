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
package org.jasig.ssp.model; // NOPMD by jon.adams on 5/24/12 1:34 PM

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Size;

/**
 * Staff should have some information stored for use in notifications to
 * appropriate users and for other reporting purposes.
 * 
 * Staff may have one associated demographic instance (one-to-one mapping).
 * Non-staff users should never have any information associated with them.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonStaffDetails // NOPMD by jon.adams on 5/24/12 1:34 PM
		extends AbstractAuditable implements Auditable {

	private static final long serialVersionUID = -5459175585426905765L;

	@Column(length = 50)
	@Size(max = 50)
	private String officeLocation;

	@Column(length = 50)
	@Size(max = 50)
	private String officeHours;

	@Column(length = 100)
	@Size(max = 100)
	private String departmentName;

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

	@Override
	protected int hashPrime() {
		return 349;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:13 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// PersonStaffDetails
		result *= hashField("officeLocation", officeLocation);
		result *= hashField("officeHours", officeHours);
		result *= hashField("departmentName", departmentName);

		return result;
	}
}