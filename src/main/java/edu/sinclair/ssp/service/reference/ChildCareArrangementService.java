package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface ChildCareArrangementService {

	public List<ChildCareArrangement> getAll(ObjectStatus status);

	public ChildCareArrangement get(UUID id) throws ObjectNotFoundException;

	public ChildCareArrangement create(ChildCareArrangement obj);

	public ChildCareArrangement save(ChildCareArrangement obj) throws ObjectNotFoundException;

	public void delete(UUID id) throws ObjectNotFoundException;

}
