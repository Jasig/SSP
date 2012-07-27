package org.jasig.ssp.service.reference;

import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * JournalStepDetail service
 */
public interface JournalStepDetailService extends
		ReferenceService<JournalStepDetail> {

	PagingWrapper<JournalStepDetail> getAllForJournalStep(
			final JournalStep journalStep,
			final SortingAndPaging sAndP);
}