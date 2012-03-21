package edu.sinclair.ssp.service.tool;

import java.util.UUID;

import edu.sinclair.ssp.model.tool.IntakeForm;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface IntakeService {

	public boolean save(IntakeForm form) throws ObjectNotFoundException;

	public IntakeForm loadForPerson(UUID studentId)
			throws ObjectNotFoundException;

}