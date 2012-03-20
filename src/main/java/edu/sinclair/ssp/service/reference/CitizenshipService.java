package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.AuditableCrudService;

public interface CitizenshipService extends AuditableCrudService<Citizenship> {

	public List<Citizenship> getAll(ObjectStatus status);

	public Citizenship get(UUID id) throws ObjectNotFoundException;

	public Citizenship create(Citizenship obj);

	public Citizenship save(Citizenship obj) throws ObjectNotFoundException;

	public void delete(UUID id) throws ObjectNotFoundException;

}
