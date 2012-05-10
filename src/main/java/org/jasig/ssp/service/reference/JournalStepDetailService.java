package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * JournalStepDetail service
 */
public interface JournalStepDetailService extends
		AuditableCrudService<JournalStepDetail> {

	@Override
	PagingWrapper<JournalStepDetail> getAll(SortingAndPaging sAndP);

	@Override
	JournalStepDetail get(UUID id) throws ObjectNotFoundException;

	@Override
	JournalStepDetail create(JournalStepDetail obj);

	@Override
	JournalStepDetail save(JournalStepDetail obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	PagingWrapper<JournalStepDetail> getAllForJournalStep(
			final JournalStep journalStep,
			final SortingAndPaging sAndP);
}
