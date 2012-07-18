package org.jasig.ssp.service;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * PersonProgramStatus service
 * 
 * @author jon.adams
 * 
 */
public interface PersonProgramStatusService
		extends PersonAssocAuditableService<PersonProgramStatus> {

	/**
	 * Gets the only program status that is not marked expired, if there is one.
	 * 
	 * @param personId
	 *            person identifier
	 * @return the only program status that is not marked expired, if there is
	 *         one
	 * @throws ObjectNotFoundException
	 *             If personId was not found.
	 */
	PersonProgramStatus getCurrent(@NotNull UUID personId)
			throws ObjectNotFoundException;

	/**
	 * Sets the program status for the specified student to Transitioned.
	 * 
	 * @param person
	 *            the person
	 * @throws ObjectNotFoundException
	 *             If person could not be found
	 * @throws ValidationException
	 *             Shouldn't be thrown, unless a bug setting the
	 *             {@link PersonProgramStatus} exists in the code.
	 */
	void setTransitionForStudent(@NotNull Person person)
			throws ObjectNotFoundException, ValidationException;
}