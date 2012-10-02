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
package org.jasig.ssp.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.model.reference.EarlyAlertReason;

/**
 * EarlyAlertRouting reference object.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EarlyAlertRouting extends AbstractAuditable implements
		Auditable {

	private static final long serialVersionUID = -4909254095616501042L;

	@ManyToOne
	@JoinColumn(name = "campus_id", nullable = false)
	private Campus campus;

	@ManyToOne
	@JoinColumn(name = "early_alert_reason_id", nullable = false)
	private EarlyAlertReason earlyAlertReason;

	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;

	/**
	 * GroupName
	 * 
	 * Optional, max length 255 characters.
	 */
	@Size(max = 255)
	private String groupName;

	/**
	 * GroupEmail
	 * 
	 * Optional, max length 255 characters.
	 */
	@Size(max = 255)
	private String groupEmail;

	/**
	 * Gets the group name
	 * 
	 * @return the group name
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * Sets the group name.
	 * 
	 * @param groupName
	 *            the group name; optional unless groupEmail non-empty; max 255
	 *            characters
	 */
	public void setGroupName(final String groupName) {
		this.groupName = groupName;
	}

	/**
	 * Gets the group email
	 * 
	 * @return the group email
	 */
	public String getGroupEmail() {
		return groupEmail;
	}

	/**
	 * Sets the group email
	 * 
	 * @param groupEmail
	 *            the group email; optional unless groupName non-empty; max 255
	 *            characters
	 */
	public void setGroupEmail(final String groupEmail) {
		this.groupEmail = groupEmail;
	}

	/**
	 * Gets the campus
	 * 
	 * @return the campus
	 */
	public Campus getCampus() {
		return campus;
	}

	/**
	 * Sets the campus
	 * 
	 * @param campus
	 *            the campus; required
	 */
	public void setCampus(@NotNull final Campus campus) {
		this.campus = campus;
	}

	/**
	 * Gets the earlyAlertReason
	 * 
	 * @return the earlyAlertReason
	 */
	public EarlyAlertReason getEarlyAlertReason() {
		return earlyAlertReason;
	}

	/**
	 * Sets the earlyAlertReason
	 * 
	 * @param earlyAlertReason
	 *            the earlyAlertReason; required
	 */
	public void setEarlyAlertReason(
			@NotNull final EarlyAlertReason earlyAlertReason) {
		this.earlyAlertReason = earlyAlertReason;
	}

	/**
	 * Gets the person
	 * 
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Sets the person
	 * 
	 * @param person
	 *            the person; optional
	 */
	public void setPerson(final Person person) {
		this.person = person;
	}

	@Override
	protected int hashPrime() {
		return 281;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		result *= hashField("groupName", groupName);
		result *= hashField("groupEmail", groupEmail);
		result *= hashField("campus", campus);
		result *= hashField("earlyAlertReason", earlyAlertReason);
		result *= hashField("person", person);

		return result;
	}
}