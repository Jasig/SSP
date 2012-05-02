package org.studentsuccessplan.ssp.service.tool;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.tool.IntakeForm;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

public interface IntakeService {

	public boolean save(IntakeForm form) throws ObjectNotFoundException;

	public IntakeForm loadForPerson(UUID studentId)
			throws ObjectNotFoundException;

}