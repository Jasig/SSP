package edu.sinclair.ssp.service.tool;

import java.util.UUID;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.tool.IntakeForm;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface IntakeService {

	public boolean save(IntakeForm form) throws ObjectNotFoundException;

	public IntakeForm loadForPerson(UUID studentId)
			throws ObjectNotFoundException;

	/**
	 * Overwrites simple and collection properties with the parameter's
	 * properties, but not the Enabled property.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwrite(Person)
	 */
	public void overwriteWithCollections(Person target, IntakeForm source)
			throws ObjectNotFoundException;
}