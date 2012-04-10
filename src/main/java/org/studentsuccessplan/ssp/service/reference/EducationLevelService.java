package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.EducationLevel;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

public interface EducationLevelService extends AuditableCrudService<EducationLevel> {

	@Override
	public List<EducationLevel> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public EducationLevel get(UUID id) throws ObjectNotFoundException;

	@Override
	public EducationLevel create(EducationLevel obj);

	@Override
	public EducationLevel save(EducationLevel obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
