/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


/**
 * This table handles notification recipients/targets that
 *  can be either a SSP Role or individual user/person.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class NotificationRecipient
		extends AbstractAuditable
		implements PersonAssocAuditable {

	private static final long serialVersionUID = 27279225191519712L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", updatable = false, nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private Person person;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notification_id", updatable = false, nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Notification notification;

	@Column(nullable = true, length = 80)
	private String sspRole;

	public NotificationRecipient() {
		super();
	}

	public NotificationRecipient(@NotNull final Person person, @NotNull final Notification notification) {
		super();
		this.setPerson(person);
		this.setNotification(notification);
	}

	public NotificationRecipient(@NotNull final String sspRole, @NotNull final Notification notification) {
		super();
		this.setSspRole(sspRole);
		this.setNotification(notification);
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(@NotNull final Person person) {
		this.person = person;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public String getSspRole() {
		return sspRole;
	}

	public void setSspRole(String sspRole) {
		this.sspRole = sspRole;
	}

	@Override
	protected int hashPrime() {
		return 5;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:15 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// PersonChallenge
		result *= hashField("notification", getNotification());
		result *= hashField("person", getPerson());
		result *= hashField("ssprole", getSspRole());

		return result;
	}
}