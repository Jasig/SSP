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

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Appointment extends AbstractAuditable
		implements PersonAssocAuditable {

	private static final long serialVersionUID = -2067714338529887268L;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	private boolean attended;

	@ManyToOne
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@Override
	protected int hashPrime() {
		return 347;
	}

	@Override
	public int hashCode() { // NOPMD
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// Appointment
		result *= hashField("startTime", startTime);
		result *= hashField("endTime", endTime);
		result *= hashField("person", person);
		result *= attended ? 3 : 5;
		return result;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}

	public Date getStartTime() {
		return startTime == null ? null : new Date(startTime.getTime());
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime == null ? null : new Date(
				startTime.getTime());
	}

	public Date getEndTime() {
		return endTime == null ? null : new Date(endTime.getTime());
	}

	public void setEndTime(final Date endTime) {
		this.endTime = endTime == null ? null : new Date(endTime.getTime());
	}

	public boolean isAttended() {
		return attended;
	}

	public void setAttended(final boolean attended) {
		this.attended = attended;
	}
}