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
 * JournalStep reference object.
 * 
 * @author daniel.bower
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalStep
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 5753546327427577229L;

	private int sortOrder;

	private boolean usedForTransition = false;

	@OneToMany(mappedBy = "journalStep")
	@javax.persistence.OrderBy("sortOrder")
	private List<JournalTrackJournalStep> journalTrackJournalSteps = new ArrayList<JournalTrackJournalStep>(
			0);

	@OneToMany(mappedBy = "journalStep")
	@javax.persistence.OrderBy("sortOrder")
	private List<JournalStepJournalStepDetail> journalStepJournalStepDetails = new ArrayList<JournalStepJournalStepDetail>(
			0);

	/**
	 * Constructor
	 */
	public JournalStep() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public JournalStep(final UUID id) {
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

	public JournalStep(final UUID id, final String name) {
		super(id, name);
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(final int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isUsedForTransition() {
		return usedForTransition;
	}

	public void setUsedForTransition(final boolean usedForTransition) {
		this.usedForTransition = usedForTransition;
	}

	public List<JournalTrackJournalStep> getJournalTrackJournalSteps() {
		return journalTrackJournalSteps;
	}

	public void setJournalTrackJournalSteps(
			final List<JournalTrackJournalStep> journalTrackJournalSteps) {
		this.journalTrackJournalSteps = journalTrackJournalSteps;
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
		return 223;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		result *= hashField("sortOrder", sortOrder);
		result *= usedForTransition ? 3 : 5;

		return result;
	}
}