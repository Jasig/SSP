package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.reference.EducationLevel;

public interface EducationLevelService {

	public List<EducationLevel> getAll();

	public EducationLevel get(UUID id);

	public EducationLevel save(EducationLevel obj);

	public void delete(UUID id);

}