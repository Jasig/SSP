package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.EducationLevel;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.AuditableCrudService;

public interface EducationLevelService extends AuditableCrudService<EducationLevel> {

	public List<EducationLevel> getAll(ObjectStatus status);

	public EducationLevel get(UUID id) throws ObjectNotFoundException;

	public EducationLevel create(EducationLevel obj);

	public EducationLevel save(EducationLevel obj) throws ObjectNotFoundException;

	public void delete(UUID id) throws ObjectNotFoundException;

}
