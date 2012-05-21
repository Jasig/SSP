package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface JournalStepService extends
		AuditableCrudService<JournalStep> {

	@Override
	PagingWrapper<JournalStep> getAll(SortingAndPaging sAndP);

	@Override
	JournalStep get(UUID id) throws ObjectNotFoundException;

	@Override
	JournalStep create(JournalStep obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	JournalStep save(JournalStep obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	PagingWrapper<JournalStep> getAllForJournalTrack(JournalTrack journalTrack,
			SortingAndPaging sAndP);
}
