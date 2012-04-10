package org.studentsuccessplan.ssp.service.tool;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.tool.IntakeForm;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

public interface IntakeService {

	public boolean save(IntakeForm form) throws ObjectNotFoundException;

	public IntakeForm loadForPerson(UUID studentId)
			throws ObjectNotFoundException;

	/**
	 * Overwrites simple and collection properties with the parameter's
	 * properties, but not the Enabled property.
	 * 
	 * @param target
	 *            Entity to copy properties to
	 * @param source
	 *            Source to use for overwrites.
	 * @exception ObjectNotFoundException
	 *                If the referenced nested entities could not be loaded from
	 *                the database.
	 */
	public void overwriteWithCollections(Person target, IntakeForm source)
			throws ObjectNotFoundException;
}