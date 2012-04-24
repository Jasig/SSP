package org.studentsuccessplan.ssp.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonChallenge;
import org.studentsuccessplan.ssp.model.PersonEducationLevel;
import org.studentsuccessplan.ssp.model.PersonFundingSource;
import org.studentsuccessplan.ssp.service.tool.IntakeService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface PersonService extends AuditableCrudService<Person> {

	@Override
	List<Person> getAll(SortingAndPaging sAndP);

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
	Person get(UUID id) throws ObjectNotFoundException;

	Person personFromUsername(String username) throws ObjectNotFoundException;

	Person personFromUserId(String userId) throws ObjectNotFoundException;

	/**
	 * Creates a new Person instance based on the supplied model.
	 * 
	 * @param obj
	 *            Model instance
	 */
	@Override
	Person create(Person obj);

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
	Person save(Person obj) throws ObjectNotFoundException;

	/**
	 * Mark a Person as deleted.
	 * 
	 * Does not remove them from persistent storage, but marks their status flag
	 * to {@link ObjectStatus#DELETED}.
	 */
	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	/**
	 * Overwrites simple properties with the parameter's properties. Does not
	 * include the Enabled property.
	 * 
	 * @param target
	 *            Target (original) to overwrite.
	 * @param source
	 *            Source to use for overwrites.
	 */
	void overwrite(Person target, Person source);

	/**
	 * Overwrites simple and collection properties with the parameter's
	 * properties, but not the Enabled property.
	 * 
	 * @param target
	 *            Target (original) to overwrite.
	 * @param source
	 *            Source to use for overwrites.
	 * @see #overwrite(Person, Person)
	 * @exception ObjectNotFoundException
	 *                If the referenced nested entities could not be loaded from
	 *                the database.
	 */
	void overwriteWithCollections(Person target, Person source)
			throws ObjectNotFoundException;

	/**
	 * Overwrites the EducationLevels property.
	 * 
	 * @param target
	 *            Target (original) to overwrite.
	 * @param source
	 *            Source to use for overwrites.
	 * @see #overwrite(Person, Person)
	 * @exception ObjectNotFoundException
	 *                If the referenced nested entities could not be loaded from
	 *                the database.
	 */
	void overwriteWithCollectionsEducationLevels(Person target,
			Set<PersonEducationLevel> source) throws ObjectNotFoundException;

	/**
	 * Overwrites the FundingSources property.
	 * 
	 * @param target
	 *            Target (original) to overwrite.
	 * @param source
	 *            Source to use for overwrites.
	 * @see #overwrite(Person, Person)
	 * @exception ObjectNotFoundException
	 *                If the referenced nested entities could not be loaded from
	 *                the database.
	 */
	void overwriteWithCollectionsFundingSources(Person target,
			Set<PersonFundingSource> source) throws ObjectNotFoundException;

	/**
	 * Overwrites the Challenges property.
	 * 
	 * @param target
	 *            Target (original) to overwrite.
	 * @param source
	 *            Source to use for overwrites.
	 * @see #overwrite(Person, Person)
	 * @exception ObjectNotFoundException
	 *                If the referenced nested entities could not be loaded from
	 *                the database.
	 */
	void overwriteWithCollectionsChallenges(Person target,
			Set<PersonChallenge> source) throws ObjectNotFoundException;
}
