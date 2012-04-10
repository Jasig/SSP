package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.VeteranStatus;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

public interface VeteranStatusService extends AuditableCrudService<VeteranStatus> {

	@Override
	public List<VeteranStatus> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public VeteranStatus get(UUID id) throws ObjectNotFoundException;

	@Override
	public VeteranStatus create(VeteranStatus obj);

	@Override
	public VeteranStatus save(VeteranStatus obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
