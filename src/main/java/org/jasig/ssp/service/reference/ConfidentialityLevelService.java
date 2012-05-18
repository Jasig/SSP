package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * ConfidentialityLevel service
 */
public interface ConfidentialityLevelService extends
		AuditableCrudService<ConfidentialityLevel> {

	@Override
	PagingWrapper<ConfidentialityLevel> getAll(SortingAndPaging sAndP);

	@Override
	ConfidentialityLevel get(UUID id) throws ObjectNotFoundException;

	@Override
	ConfidentialityLevel create(ConfidentialityLevel obj)
			throws ObjectNotFoundException, ValidationException;

	@Override
	ConfidentialityLevel save(ConfidentialityLevel obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}