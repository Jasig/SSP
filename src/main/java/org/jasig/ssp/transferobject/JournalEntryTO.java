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
package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlyDeserializer;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlySerializer;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelLiteTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;

import com.google.common.collect.Sets;

/**
 * JournalEntry transfer object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JournalEntryTO
		extends AbstractAuditableTO<JournalEntry>
		implements TransferObject<JournalEntry>, Serializable {

	private static final long serialVersionUID = -2188963893970704753L;

	@JsonSerialize(using = DateOnlySerializer.class)
	@JsonDeserialize(using = DateOnlyDeserializer.class)
	@NotNull
	private Date entryDate;

	private String comment;

	private UUID personId;

	@NotNull
	private ReferenceLiteTO<JournalSource> journalSource;

	private ReferenceLiteTO<JournalTrack> journalTrack;

	@NotNull
	private ConfidentialityLevelLiteTO confidentialityLevel;

	private Set<JournalEntryDetailTO> journalEntryDetails = Sets.newHashSet();

	/**
	 * Empty constructor
	 */
	public JournalEntryTO() {
		super();
	}

	public JournalEntryTO(final JournalEntry journalEntry) {
		super();
		from(journalEntry);
	}

	@Override
	public final void from(final JournalEntry journalEntry) { // NOPMD
		super.from(journalEntry);

		entryDate = journalEntry.getEntryDate();
		comment = journalEntry.getComment();
		personId = journalEntry.getPerson() == null ? null
				: journalEntry.getPerson().getId();
		journalSource = journalEntry.getJournalSource() == null ? null
				: new ReferenceLiteTO<JournalSource>(
						journalEntry.getJournalSource());
		journalTrack = journalEntry.getJournalTrack() == null ? null
				: new ReferenceLiteTO<JournalTrack>(
						journalEntry.getJournalTrack());

		confidentialityLevel = ConfidentialityLevelLiteTO.fromModel(
				journalEntry.getConfidentialityLevel());

		if (journalEntry.getJournalEntryDetails() != null
				&& !journalEntry.getJournalEntryDetails().isEmpty()) {
			journalEntryDetails = JournalEntryDetailTO.toTOSet(journalEntry
					.getJournalEntryDetails());
		}
	}

	/**
	 * Convert a collection of JournalEntry models to equivalent transfer
	 * objects.
	 * 
	 * @param journalEntries
	 *            A collection of models
	 * @return A list of JournalEntry transfer objects
	 */
	public static List<JournalEntryTO> toTOList(
			final Collection<JournalEntry> journalEntries) {
		final List<JournalEntryTO> journalEntryTOs = new ArrayList<JournalEntryTO>();
		if ((journalEntries != null) && !journalEntries.isEmpty()) {
			for (final JournalEntry journalEntry : journalEntries) {
				journalEntryTOs.add(new JournalEntryTO(journalEntry)); // NOPMD
			}
		}

		return journalEntryTOs;
	}

	/**
	 * Gets the entry date
	 * 
	 * @return the entry date
	 */
	public Date getEntryDate() {
		return entryDate == null ? null : new Date(entryDate.getTime());
	}

	/**
	 * Sets the entry date
	 * 
	 * @param entryDate
	 *            the entry date
	 */
	public void setEntryDate(final Date entryDate) {
		this.entryDate = entryDate == null ? null : new Date(
				entryDate.getTime());
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public ReferenceLiteTO<JournalSource> getJournalSource() {
		return journalSource;
	}

	public void setJournalSource(
			@NotNull final ReferenceLiteTO<JournalSource> journalSource) {
		this.journalSource = journalSource;
	}

	public ReferenceLiteTO<JournalTrack> getJournalTrack() {
		return journalTrack;
	}

	public void setJournalTrack(
			final ReferenceLiteTO<JournalTrack> journalTrack) {
		this.journalTrack = journalTrack;
	}

	public ConfidentialityLevelLiteTO getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(
			@NotNull final ConfidentialityLevelLiteTO confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	public Set<JournalEntryDetailTO> getJournalEntryDetails() {
		return journalEntryDetails;
	}

	public void setJournalEntryDetails(
			final Set<JournalEntryDetailTO> journalEntryDetails) {
		this.journalEntryDetails = journalEntryDetails;
	}
}