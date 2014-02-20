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
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jasig.ssp.model.reference.CompletedItem;

/**
 * Students should have some Education Plan stored for use in notifications to
 * appropriate users, and for reporting purposes.
 * 
 * Students may have one associated plan instance (one-to-one mapping).
 * Non-student users should never have any plan associated to them.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonCompletedItem
extends AbstractAuditable
implements PersonAssocAuditable {

	private static final long serialVersionUID = 1818887030744791834L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "completed_item_id", nullable = false)
	private CompletedItem completedItem;

	
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Person person;

	@Override
	protected int hashPrime() {
		return 19;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:14 PM
		int result = hashPrime();

		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		result *= hashField("completedItem", getCompletedItem());
		result *= hashField("person", getPerson());

		return result;
	}

	public CompletedItem getCompletedItem() {
		return completedItem;
	}

	public void setCompletedItems(CompletedItem completedItems) {
		this.completedItem = completedItems;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}