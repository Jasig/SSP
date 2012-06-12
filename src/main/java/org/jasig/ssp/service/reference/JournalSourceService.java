package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface JournalSourceService extends
		AuditableCrudService<JournalSource> {

	@Override
	PagingWrapper<JournalSource> getAll(SortingAndPaging sAndP);

	@Override
	JournalSource get(UUID id) throws ObjectNotFoundException;

	@Override
	JournalSource create(JournalSource obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	JournalSource save(JournalSource obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}