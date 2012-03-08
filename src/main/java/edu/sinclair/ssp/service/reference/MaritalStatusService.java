package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface MaritalStatusService {

	public List<MaritalStatus> getAll(ObjectStatus status);

	public MaritalStatus get(UUID id) throws ObjectNotFoundException;

	public MaritalStatus create(MaritalStatus obj);

	public MaritalStatus save(MaritalStatus obj) throws ObjectNotFoundException;

	public void delete(UUID id) throws ObjectNotFoundException;

}
