package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface JournalSourceService extends
		AuditableCrudService<JournalSource> {

	@Override
	public PagingWrapper<JournalSource> getAll(SortingAndPaging sAndP);

	@Override
	public JournalSource get(UUID id) throws ObjectNotFoundException;

	@Override
	public JournalSource create(JournalSource obj);

	@Override
	public JournalSource save(JournalSource obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
