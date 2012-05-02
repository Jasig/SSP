package org.jasig.ssp.service.tool;

import java.util.UUID;

import org.jasig.ssp.model.tool.IntakeForm;
import org.jasig.ssp.service.ObjectNotFoundException;

public interface IntakeService {

	public boolean save(IntakeForm form) throws ObjectNotFoundException;

	public IntakeForm loadForPerson(UUID studentId)
			throws ObjectNotFoundException;

}