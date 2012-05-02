package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.MaritalStatus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface MaritalStatusService extends
		AuditableCrudService<MaritalStatus> {

	@Override
	public PagingWrapper<MaritalStatus> getAll(SortingAndPaging sAndP);

	@Override
	public MaritalStatus get(UUID id) throws ObjectNotFoundException;

	@Override
	public MaritalStatus create(MaritalStatus obj);

	@Override
	public MaritalStatus save(MaritalStatus obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
