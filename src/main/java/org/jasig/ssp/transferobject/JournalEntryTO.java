package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.JournalEntryJournalStepDetail;
import org.jasig.ssp.transferobject.reference.JournalStepDetailTO;

import com.google.common.collect.Lists;

public class JournalEntryTO
		extends AuditableTO<JournalEntry>
		implements TransferObject<JournalEntry>, Serializable {

	private static final long serialVersionUID = 1L;

	private Date entryDate;
	private String comment;
	private UUID personId, journalSourceId, journalTrackId,
			confidentialityLevel;
	private List<JournalStepDetailTO> journalStepDetails;

	public JournalEntryTO() {
		super();
	}

	public JournalEntryTO(final JournalEntry journalEntry) {
		super();
		from(journalEntry);
	}

	@Override
	public final void from(final JournalEntry journalEntry) {
		super.from(journalEntry);

		entryDate = journalEntry.getEntryDate();
		comment = journalEntry.getComment();
		personId = (journalEntry.getPerson() == null) ? null
				: journalEntry.getPerson().getId();
		journalSourceId = (journalEntry.getJournalSource() == null) ? null
				: journalEntry.getJournalSource().getId();
		journalTrackId = (journalEntry.getJournalTrack() == null) ? null
				: journalEntry.getJournalTrack().getId();
		confidentialityLevel = (journalEntry.getConfidentialityLevel() == null) ? null
				: journalEntry.getConfidentialityLevel().getId();

		journalStepDetails = Lists.newArrayList();
		if ((journalEntry.getJournalEntryJournalStepDetails() != null)
				&& !journalEntry.getJournalEntryJournalStepDetails().isEmpty()) {
			for (JournalEntryJournalStepDetail stepDetail : journalEntry
					.getJournalEntryJournalStepDetails()) {
				journalStepDetails.add(new JournalStepDetailTO(stepDetail
						.getJournalStepDetail()));
			}
		}

	}

	public static List<JournalEntryTO> toTOList(
			final Collection<JournalEntry> journalEntries) {
		final List<JournalEntryTO> journalEntryTOs = new ArrayList<JournalEntryTO>();
		if ((journalEntries != null) && !journalEntries.isEmpty()) {
			for (JournalEntry journalEntry : journalEntries) {
				journalEntryTOs.add(new JournalEntryTO(journalEntry));
			}
		}
		return journalEntryTOs;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(final Date entryDate) {
		this.entryDate = entryDate;
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

	public UUID getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(final UUID confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	public List<JournalStepDetailTO> getJournalStepDetails() {
		return journalStepDetails;
	}

	public void setJournalStepDetails(
			final List<JournalStepDetailTO> journalStepDetails) {
		this.journalStepDetails = journalStepDetails;
	}

}
