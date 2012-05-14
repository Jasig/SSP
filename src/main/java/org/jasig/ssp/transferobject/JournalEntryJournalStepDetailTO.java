package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.JournalEntryJournalStepDetail;
import org.jasig.ssp.transferobject.reference.JournalStepDetailTO;

/**
 * JournalEntryJournalStepDetail transfer object
 */
public class JournalEntryJournalStepDetailTO extends
		AuditableTO<JournalEntryJournalStepDetail> implements
		TransferObject<JournalEntryJournalStepDetail>, Serializable {

	private static final long serialVersionUID = -2753609690043435376L;

	private UUID journalEntryId;

	private JournalStepDetailTO journalStepDetail;

	public JournalEntryJournalStepDetailTO() {
		super();
	}

	public JournalEntryJournalStepDetailTO(
			final JournalEntryJournalStepDetail model) {
		super();
		from(model);
	}

	@Override
	public final void from(final JournalEntryJournalStepDetail model) {
		super.from(model);

		journalEntryId = model.getJournalEntry() == null ? null : model
				.getJournalEntry().getId();
		journalStepDetail = model.getJournalStepDetail() == null ? null
				: new JournalStepDetailTO(model.getJournalStepDetail());
	}

	public static List<JournalEntryJournalStepDetailTO> toTOList(
			final Collection<JournalEntryJournalStepDetail> models) {
		final List<JournalEntryJournalStepDetailTO> tos = new ArrayList<JournalEntryJournalStepDetailTO>();
		if ((models != null) && !models.isEmpty()) {
			for (JournalEntryJournalStepDetail model : models) {
				tos.add(new JournalEntryJournalStepDetailTO(model)); // NOPMD
			}
		}

		return tos;
	}

	public UUID getJournalEntryId() {
		return journalEntryId;
	}

	public void setJournalEntryId(final UUID journalEntryId) {
		this.journalEntryId = journalEntryId;
	}

	public JournalStepDetailTO getJournalStepDetail() {
		return journalStepDetail;
	}

	public void setJournalStepDetail(final JournalStepDetailTO journalStepDetail) {
		this.journalStepDetail = journalStepDetail;
	}

}
