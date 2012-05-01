package org.studentsuccessplan.ssp.service.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.VeteranStatus;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

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
