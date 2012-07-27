package org.jasig.ssp.service.reference;

import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.model.reference.JournalTrackJournalStep;
import org.jasig.ssp.service.ReferenceService;

/**
 * JournalTrackService
 */
public interface JournalTrackService extends
		ReferenceService<JournalTrack> {

	JournalTrackJournalStep addJournalStepToJournalTrack(
			JournalStep journalStep,
			JournalTrack journalTrack);

	JournalTrackJournalStep removeJournalStepFromJournalTrack(
			JournalStep journalStep,
			JournalTrack journalTrack);
}
