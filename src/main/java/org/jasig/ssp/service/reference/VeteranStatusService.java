package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.VeteranStatus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface VeteranStatusService extends
		AuditableCrudService<VeteranStatus> {

	@Override
	public PagingWrapper<VeteranStatus> getAll(SortingAndPaging sAndP);

	@Override
	public VeteranStatus get(UUID id) throws ObjectNotFoundException;

	@Override
	public VeteranStatus create(VeteranStatus obj);

	@Override
	public VeteranStatus save(VeteranStatus obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
