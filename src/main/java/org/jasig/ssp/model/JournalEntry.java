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
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.model.reference.JournalTrack;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalEntry
		extends AbstractAuditable
		implements RestrictedPersonAssocAuditable {

	private static final long serialVersionUID = 1477217415946557983L;

	@Temporal(TemporalType.DATE)
	private Date entryDate;

	private String comment;

	@Nullable()
	@ManyToOne()
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "confidentiality_level_id", nullable = false)
	private ConfidentialityLevel confidentialityLevel;

	@ManyToOne
	@JoinColumn(name = "journal_source_id", nullable = false)
	private JournalSource journalSource;

	@ManyToOne
	@JoinColumn(name = "journal_track_id", nullable = true)
	private JournalTrack journalTrack;

	/**
	 * Each JournalEntry may have multiple JournalEntryDetails. Each one points
	 * to a association between the two reference entities JournalStep and
	 * JournalStepDetail that have a many-to-many association between each
	 * other.
	 * 
	 * <p>
	 * Changes to this set are persisted, since this side is the owning side of
	 * the Hibernate relationship because of the "mappedBy" and CascadeTypes of
	 * PERSIST and MERGE.
	 */
	@OneToMany(mappedBy = "journalEntry")
	@Cascade(value = { CascadeType.ALL })
	private Set<JournalEntryDetail> journalEntryDetails = new HashSet<JournalEntryDetail>();

	@ManyToOne
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@Override
	public ConfidentialityLevel getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(
			final ConfidentialityLevel confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	public Date getEntryDate() {
		return entryDate == null ? null : new Date(entryDate.getTime());
	}

	public void setEntryDate(@NotNull final Date entryDate) {
		this.entryDate = entryDate == null ? null : new Date(
				entryDate.getTime());
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public JournalSource getJournalSource() {
		return journalSource;
	}

	public void setJournalSource(@NotNull final JournalSource journalSource) {
		this.journalSource = journalSource;
	}

	public JournalTrack getJournalTrack() {
		return journalTrack;
	}

	public void setJournalTrack(final JournalTrack journalTrack) {
		this.journalTrack = journalTrack;
	}

	public Set<JournalEntryDetail> getJournalEntryDetails() {
		return journalEntryDetails;
	}

	public void setJournalEntryDetails(
			final Set<JournalEntryDetail> journalEntryDetails) {
		this.journalEntryDetails = journalEntryDetails == null ? new HashSet<JournalEntryDetail>()
				: journalEntryDetails;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(@NotNull final Person person) {
		this.person = person;
	}

	@Override
	protected int hashPrime() {
		return 241;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/14/12 1:49 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		result *= hashField("entryDate", entryDate);
		result *= hashField("comment", comment);
		result *= hashField("confidentialityLevel", confidentialityLevel);
		result *= hashField("journalSource", journalSource);
		result *= hashField("journalTrack", journalTrack);
		result *= hashField("person", person);

		// collections are not included in these calculations

		return result;
	}
}
