package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelLiteTO;

/**
 * JournalEntry transfer object
 */
public class JournalEntryTO
		extends AbstractAuditableTO<JournalEntry>
		implements TransferObject<JournalEntry>, Serializable {

	private static final long serialVersionUID = -2188963893970704753L;

	private Date entryDate;

	private String comment;

	private UUID personId, journalSourceId, journalTrackId;

	private ConfidentialityLevelLiteTO confidentialityLevel;

	private List<JournalEntryJournalStepDetailTO> journalEntryJournalStepDetails;

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
		journalSourceId = journalEntry.getJournalSource() == null ? null
				: journalEntry.getJournalSource().getId();
		journalTrackId = journalEntry.getJournalTrack() == null ? null
				: journalEntry.getJournalTrack().getId();

		confidentialityLevel = ConfidentialityLevelLiteTO.fromModel(
				journalEntry.getConfidentialityLevel());

		if ((journalEntry.getJournalEntryJournalStepDetails() != null)
				&& (journalEntry.getJournalEntryJournalStepDetails().size() > 0)) {
			journalEntryJournalStepDetails = JournalEntryJournalStepDetailTO
					.toTOList(journalEntry.getJournalEntryJournalStepDetails());
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

	public UUID getJournalSourceId() {
		return journalSourceId;
	}

	public void setJournalSourceId(final UUID journalSourceId) {
		this.journalSourceId = journalSourceId;
	}

	public UUID getJournalTrackId() {
		return journalTrackId;
	}

	public void setJournalTrackId(final UUID journalTrackId) {
		this.journalTrackId = journalTrackId;
	}

	public ConfidentialityLevelLiteTO getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(
			final ConfidentialityLevelLiteTO confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	public List<JournalEntryJournalStepDetailTO> getJournalEntryJournalStepDetails() {
		return journalEntryJournalStepDetails;
	}

	public void setJournalEntryJournalStepDetails(
			final List<JournalEntryJournalStepDetailTO> journalEntryJournalStepDetails) {
		this.journalEntryJournalStepDetails = journalEntryJournalStepDetails;
	}
}