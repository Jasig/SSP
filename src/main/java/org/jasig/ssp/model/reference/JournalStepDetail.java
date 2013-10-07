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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OrderBy;
import org.jasig.ssp.model.Auditable;

/**
 * JournalStepDetail reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalStepDetail
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 5855955069509045667L;

	private int sortOrder;

	@OneToMany(mappedBy = "journalStepDetail")
	private List<JournalStepJournalStepDetail> journalStepJournalStepDetails = new ArrayList<JournalStepJournalStepDetail>(
			0);

	/**
	 * Constructor
	 */
	public JournalStepDetail() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public JournalStepDetail(final UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 */

	public JournalStepDetail(final UUID id, final String name) {
		super(id, name);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 * @param description
	 *            Description; max 150 characters
	 */
	public JournalStepDetail(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(final int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<JournalStepJournalStepDetail> getJournalStepJournalStepDetails() {
		return journalStepJournalStepDetails;
	}

	public void setJournalStepJournalStepDetails(
			final List<JournalStepJournalStepDetail> journalStepJournalStepDetails) {
		this.journalStepJournalStepDetails = journalStepJournalStepDetails;
	}

	@Override
	protected int hashPrime() {
		return 229;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		result *= hashField("sortOrder", sortOrder);

		return result;
	}
}