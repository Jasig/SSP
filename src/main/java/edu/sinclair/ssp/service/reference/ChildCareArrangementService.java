package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.AuditableCrudService;

public interface ChildCareArrangementService extends
		AuditableCrudService<ChildCareArrangement> {

	public List<ChildCareArrangement> getAll(ObjectStatus status);

	public ChildCareArrangement get(UUID id) throws ObjectNotFoundException;

	public ChildCareArrangement create(ChildCareArrangement obj);

	public ChildCareArrangement save(ChildCareArrangement obj)
			throws ObjectNotFoundException;

	public void delete(UUID id) throws ObjectNotFoundException;

}
