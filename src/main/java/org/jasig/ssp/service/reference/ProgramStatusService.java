package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * ProgramStatus service
 * 
 * @author jon.adams
 */
public interface ProgramStatusService extends
		AuditableCrudService<ProgramStatus> {

	@Override
	PagingWrapper<ProgramStatus> getAll(SortingAndPaging sAndP);

	@Override
	ProgramStatus get(UUID id) throws ObjectNotFoundException;

	@Override
	ProgramStatus create(ProgramStatus obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	ProgramStatus save(ProgramStatus obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}