package org.jasig.ssp.service.reference;

import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * JournalStep service
 */
public interface JournalStepService extends
		ReferenceService<JournalStep> {

	/**
	 * Get all Steps for the specified Track
	 * 
	 * @param journalTrack
	 *            the track
	 * @param sAndP
	 *            sorting and paging parameters
	 * @return All Steps for the specified Track
	 */
	PagingWrapper<JournalStep> getAllForJournalTrack(JournalTrack journalTrack,
			SortingAndPaging sAndP);

	JournalStepJournalStepDetail addJournalStepDetailToJournalStep(
			JournalStepDetail journalStepDetail, JournalStep journalStep);

	JournalStepJournalStepDetail removeJournalStepDetailFromJournalStep(
			JournalStepDetail journalStepDetail, JournalStep journalStep);
}