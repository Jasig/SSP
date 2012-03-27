package edu.sinclair.ssp.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonChallenge;
import edu.sinclair.ssp.model.PersonEducationLevel;
import edu.sinclair.ssp.model.PersonFundingSource;
import edu.sinclair.ssp.service.tool.IntakeService;

public interface PersonService extends AuditableCrudService<Person> {

	/**
	 * Retrieve every Person instance in the database filtered by the supplied
	 * status.
	 * 
	 * @param status
	 *            Filter by this status, usually null or
	 *            {@link ObjectStatus#DELETED}.
	 * @return List of all people in the database filtered by the supplied
	 *         status.
	 */
	@Override
	public List<Person> getAll(ObjectStatus status);

	/**
	 * Retrieves the specified Person.
	 * 
	 * @param id
	 *            Required identifier for the Person to retrieve. Can not be
	 *            null.
	 * @exception ObjectNotFoundException
	 *                If the supplied identifier does not exist in the database.
	 * @return The specified Person instance.
	 */
	@Override
	public Person get(UUID id) throws ObjectNotFoundException;

	public Person personFromUsername(String username)
			throws ObjectNotFoundException;

	/**
	 * Creates a new Person instance based on the supplied model.
	 * 
	 * @param obj
	 *            Model instance
	 */
	@Override
	public Person create(Person obj);

	/**
	 * Updates values of direct Person properties, but not any associated
	 * children or collections.
	 * 
	 * WARNING: Copies system-only (based on business logic rules) properties,
	 * so ensure that the incoming values have already been sanitized.
	 * 
	 * @param obj
	 *            Model instance from which to copy the simple properties.
	 * @see IntakeService
	 */
	@Override
	public Person save(Person obj) throws ObjectNotFoundException;

	/**
	 * Mark a Person as deleted.
	 * 
	 * Does not remove them from persistent storage, but marks their status flag
	 * to {@link ObjectStatus#DELETED}.
	 */
	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

	/**
	 * Overwrites simple properties with the parameter's properties. Does not
	 * include the Enabled property.
	 * 
	 * @param target
	 *            Target (original) to overwrite.
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwriteWithEnabled(Person)
	 * @see overwriteWithEnabledAndCollections(Person)
	 */
	public void overwrite(Person target, Person source);

	/**
	 * Overwrites simple and collection properties with the parameter's
	 * properties, but not the Enabled property.
	 * 
	 * @param target
	 *            Target (original) to overwrite.
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwrite(Person)
	 */
	public void overwriteWithCollections(Person target, Person source)
			throws ObjectNotFoundException;

	/**
	 * Overwrites the EducationLevels property.
	 * 
	 * @param target
	 *            Target (original) to overwrite.
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwrite(Person)
	 */
	public void overwriteWithCollectionsEducationLevels(Person target,
			Set<PersonEducationLevel> source) throws ObjectNotFoundException;

	/**
	 * Overwrites the FundingSources property.
	 * 
	 * @param target
	 *            Target (original) to overwrite.
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwrite(Person)
	 */
	public void overwriteWithCollectionsFundingSources(Person target,
			Set<PersonFundingSource> source) throws ObjectNotFoundException;

	/**
	 * Overwrites the Challenges property.
	 * 
	 * @param target
	 *            Target (original) to overwrite.
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwrite(Person)
	 */
	public void overwriteWithCollectionsChallenges(Person target,
			Set<PersonChallenge> source) throws ObjectNotFoundException;
}
