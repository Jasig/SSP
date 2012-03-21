package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.AuditableCrudService;

public interface VeteranStatusService extends
		AuditableCrudService<VeteranStatus> {

	public List<VeteranStatus> getAll(ObjectStatus status);

	public VeteranStatus get(UUID id) throws ObjectNotFoundException;

	public VeteranStatus create(VeteranStatus obj);

	public VeteranStatus save(VeteranStatus obj) throws ObjectNotFoundException;

	public void delete(UUID id) throws ObjectNotFoundException;

}
