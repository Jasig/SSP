package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.EducationGoal;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface EducationGoalService extends AuditableCrudService<EducationGoal> {

	@Override
	public List<EducationGoal> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public EducationGoal get(UUID id) throws ObjectNotFoundException;

	@Override
	public EducationGoal create(EducationGoal obj);

	@Override
	public EducationGoal save(EducationGoal obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
