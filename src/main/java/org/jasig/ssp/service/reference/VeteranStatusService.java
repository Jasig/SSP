package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.VeteranStatus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * VeteranStatus service
 * 
 * @author daniel.bower
 */
public interface VeteranStatusService extends
		AuditableCrudService<VeteranStatus> {

	@Override
	PagingWrapper<VeteranStatus> getAll(SortingAndPaging sAndP);

	@Override
	VeteranStatus get(UUID id) throws ObjectNotFoundException;

	@Override
	VeteranStatus create(VeteranStatus obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	VeteranStatus save(VeteranStatus obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}