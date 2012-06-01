package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.model.reference.JournalTrackJournalStep;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * JournalTrackService
 */
public interface JournalTrackService extends
		AuditableCrudService<JournalTrack> {

	@Override
	PagingWrapper<JournalTrack> getAll(SortingAndPaging sAndP);

	@Override
	JournalTrack get(UUID id) throws ObjectNotFoundException;

	@Override
	JournalTrack create(JournalTrack obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	JournalTrack save(JournalTrack obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	JournalTrackJournalStep addJournalStepToJournalTrack(
			JournalStep journalStep,
			JournalTrack journalTrack);

	JournalTrackJournalStep removeJournalStepFromJournalTrack(
			JournalStep journalStep,
			JournalTrack journalTrack);
}
