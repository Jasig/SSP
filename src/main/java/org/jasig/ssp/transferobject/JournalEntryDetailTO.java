package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.JournalEntryDetail;
import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;
import org.jasig.ssp.transferobject.reference.JournalStepDetailTO;
import org.jasig.ssp.transferobject.reference.JournalStepTO;

/**
 * JournalEntryDetail transfer object
 */
public class JournalEntryDetailTO extends
		AbstractAuditableTO<JournalEntryDetail> implements
		TransferObject<JournalEntryDetail>, Serializable {

	private static final long serialVersionUID = -2753609690043435376L;

	private UUID journalEntryId;

	private JournalStepTO journalStep;

	private JournalStepDetailTO journalStepDetail;

	public JournalEntryDetailTO() {
		super();
	}

	public JournalEntryDetailTO(
			final JournalEntryDetail model) {
		super();
		from(model);
	}

	@Override
	public final void from(final JournalEntryDetail model) {
		super.from(model);

		journalEntryId = model.getJournalEntry() == null ? null : model
				.getJournalEntry().getId();

		final JournalStepJournalStepDetail jsJsDetail = model
				.getJournalStepJournalStepDetail();
		if (jsJsDetail == null) {
			journalStep = null;
			journalStepDetail = null;
		} else {
			journalStep = jsJsDetail.getJournalStep() == null ? null
					: new JournalStepTO(jsJsDetail.getJournalStep());
			journalStepDetail = jsJsDetail.getJournalStepDetail() == null ? null
					: new JournalStepDetailTO(jsJsDetail.getJournalStepDetail());
		}
	}

	public static List<JournalEntryDetailTO> toTOList(
			final Collection<JournalEntryDetail> models) {
		final List<JournalEntryDetailTO> tos = new ArrayList<JournalEntryDetailTO>();
		if ((models != null) && !models.isEmpty()) {
			for (JournalEntryDetail model : models) {
				tos.add(new JournalEntryDetailTO(model)); // NOPMD
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

	public JournalStepTO getJournalStep() {
		return journalStep;
	}

	public void setJournalStep(final JournalStepTO journalStep) {
		this.journalStep = journalStep;
	}

	public JournalStepDetailTO getJournalStepDetail() {
		return journalStepDetail;
	}

	public void setJournalStepDetail(final JournalStepDetailTO journalStepDetail) {
		this.journalStepDetail = journalStepDetail;
	}

}
