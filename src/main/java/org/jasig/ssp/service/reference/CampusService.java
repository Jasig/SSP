package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * Campus service
 * 
 * @author jon.adams
 */
public interface CampusService extends
		AuditableCrudService<Campus> {

	@Override
	PagingWrapper<Campus> getAll(SortingAndPaging sAndP);

	@Override
	Campus get(UUID id) throws ObjectNotFoundException;

	@Override
	Campus create(Campus obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	Campus save(Campus obj) throws ObjectNotFoundException, ValidationException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
