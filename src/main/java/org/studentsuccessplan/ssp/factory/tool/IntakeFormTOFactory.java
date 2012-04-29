package org.studentsuccessplan.ssp.factory.tool;

import org.studentsuccessplan.ssp.model.tool.IntakeForm;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.transferobject.tool.IntakeFormTO;
import org.studentsuccessplan.ssp.web.api.validation.ValidationException;

public interface IntakeFormTOFactory {
	IntakeFormTO from(IntakeForm model);

	IntakeForm from(IntakeFormTO tObject) throws ObjectNotFoundException, ValidationException;
}
