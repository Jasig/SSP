package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.EducationGoal;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.AuditableCrudService;

public interface EducationGoalService extends AuditableCrudService<EducationGoal> {

	public List<EducationGoal> getAll(ObjectStatus status);

	public EducationGoal get(UUID id) throws ObjectNotFoundException;

	public EducationGoal create(EducationGoal obj);

	public EducationGoal save(EducationGoal obj) throws ObjectNotFoundException;

	public void delete(UUID id) throws ObjectNotFoundException;

}
