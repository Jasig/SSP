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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class WatchStudent extends AbstractAuditable implements PersonAssocAuditable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Immutable
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "student_id", updatable = false, nullable = false)
	private Person student;
	
	@Immutable
	@NotNull
	@ManyToOne
	@JoinColumn(name = "watcher_id", updatable = false, nullable = false)
	private Person person;
	
	/**
	 * Entity creation time stamp.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = true)
	private Date watchDate;

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}


	@Override
	protected int hashPrime() {
		return 107;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		result *= hashField("person", person);
		result *= hashField("person", student);
		

		return result;
	}

	public Person getStudent() {
		return student;
	}

	public void setStudent(Person student) {
		this.student = student;
	}

	public Date getWatchDate() {
		return watchDate;
	}

	public void setWatchDate(Date watchDate) {
		this.watchDate = watchDate;
	}
}