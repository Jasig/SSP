package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.jasig.ssp.model.JournalEntryDetail;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;

import com.google.common.collect.Sets;

/**
 * JournalEntryDetail transfer object
 */
public class JournalEntryDetailTO extends
		AbstractAuditableTO<JournalEntryDetail> implements
		TransferObject<JournalEntryDetail>, Serializable {

	private static final long serialVersionUID = -2753609690043435376L;

	private UUID journalEntryId;

	private ReferenceLiteTO<JournalStep> journalStep;

	private Set<ReferenceLiteTO<JournalStepDetail>> journalStepDetails = Sets
			.newHashSet();

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
			journalStep = null; // NOPMD
			journalStepDetails = null; // NOPMD
		} else {
			journalStep = jsJsDetail.getJournalStep() == null ? null
					: new ReferenceLiteTO<JournalStep>(
							jsJsDetail.getJournalStep());

			final Set<ReferenceLiteTO<JournalStepDetail>> newSet = Sets
					.newHashSet();
			if (jsJsDetail.getJournalStepDetail() != null) {
				newSet.add(new ReferenceLiteTO<JournalStepDetail>(jsJsDetail
						.getJournalStepDetail()));
			}

			journalStepDetails = newSet;
		}
	}

	public static Set<JournalEntryDetailTO> toTOSet(
			final Collection<JournalEntryDetail> models) {
		final Set<JournalEntryDetailTO> tos = Sets.newHashSet();
		if ((models != null) && !models.isEmpty()) {
			for (final JournalEntryDetail model : models) {
				boolean found = false;
				final JournalEntryDetailTO jedTo = new JournalEntryDetailTO( // NOPMD
						model);

				// check for existing JournalEntryDetail in the set being built,
				// so it can be combined in the TO
				for (final JournalEntryDetailTO to : tos) {
					if (to.getJournalStep().equals(jedTo.getJournalStep())) {
						found = true;
						to.getJournalStepDetails()
								.add(jedTo.getJournalStepDetails().iterator()
										.next());
					}
				}

				if (!found) {
					tos.add(jedTo);
				}
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

	public ReferenceLiteTO<JournalStep> getJournalStep() {
		return journalStep;
	}

	public void setJournalStep(final ReferenceLiteTO<JournalStep> journalStep) {
		this.journalStep = journalStep;
	}

	/**
	 * Combined JournalStepDetails per JournalStep. However, if created by
	 * itself, then this Set will only ever contain one item. But if this
	 * JournalEntryDetailTO was created with toTOSet, then it will combine all
	 * JournalStepDetail instances with matching JournalSteps into one
	 * JournalEntryDetailTO.
	 * 
	 * @return Combined JournalStepDetails
	 */
	public Set<ReferenceLiteTO<JournalStepDetail>> getJournalStepDetails() {
		return journalStepDetails;
	}

	public void setJournalStepDetails(
			final Set<ReferenceLiteTO<JournalStepDetail>> journalStepDetails) {
		this.journalStepDetails = journalStepDetails;
	}
}