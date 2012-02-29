package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.reference.EducationGoal;

public interface EducationGoalService {

	public List<EducationGoal> getAll();

	public EducationGoal get(UUID id);

	public EducationGoal save(EducationGoal obj);

	public void delete(UUID id);

}