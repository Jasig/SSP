package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface JournalStepService extends
		AuditableCrudService<JournalStep> {

	@Override
	public PagingWrapper<JournalStep> getAll(SortingAndPaging sAndP);

	@Override
	public JournalStep get(UUID id) throws ObjectNotFoundException;

	@Override
	public JournalStep create(JournalStep obj);

	@Override
	public JournalStep save(JournalStep obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
