package org.jasig.ssp.factory.tool;

import org.jasig.ssp.model.tool.IntakeForm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.tool.IntakeFormTO;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface IntakeFormTOFactory {
	IntakeFormTO from(IntakeForm model);

	IntakeForm from(IntakeFormTO tObject) throws ObjectNotFoundException, ValidationException;
}
