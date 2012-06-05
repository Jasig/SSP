package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;
import org.jasig.ssp.transferobject.AbstractAuditableTO;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class JournalStepJournalStepDetailTO extends
		AbstractAuditableTO<JournalStepJournalStepDetail> implements
		TransferObject<JournalStepJournalStepDetail>, Serializable {

	private static final long serialVersionUID = 2080443166898776919L;

	private JournalStepTO journalStep;

	private JournalStepDetailTO journalStepDetail;

	public JournalStepJournalStepDetailTO() {
		super();
	}

	public JournalStepJournalStepDetailTO(
			final JournalStepJournalStepDetail model) {
		super();
		from(model);
	}

	@Override
	public final void from(final JournalStepJournalStepDetail model) {
		super.from(model);

		journalStep = model.getJournalStep() == null ? null
				: new JournalStepTO(model.getJournalStep());
		journalStepDetail = model.getJournalStepDetail() == null ? null
				: new JournalStepDetailTO(model.getJournalStepDetail());
	}

	public static List<JournalStepJournalStepDetailTO> toTOList(
			final Collection<JournalStepJournalStepDetail> models) {
		final List<JournalStepJournalStepDetailTO> tos = Lists.newArrayList();
		if ((models != null) && !models.isEmpty()) {
			for (JournalStepJournalStepDetail model : models) {
				tos.add(new JournalStepJournalStepDetailTO(model)); // NOPMD
			}
		}

		return tos;
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
