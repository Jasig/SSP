package org.jasig.ssp.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.jasig.ssp.dao.ObjectExistsException;
import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.security.PersonAttributesResult;
import org.jasig.ssp.security.exception.UnableToCreateAccountException;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonAttributesService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.service.external.RegistrationStatusByTermService;
import org.jasig.ssp.service.tool.IntakeService;
import org.jasig.ssp.transferobject.reports.AddressLabelSearchTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * Person service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	public static final boolean ALL_AUTHENTICATED_USERS_CAN_CREATE_ACCOUNT = true;

	public static final String PERMISSION_TO_CREATE_ACCOUNT = "ROLE_CAN_CREATE";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonServiceImpl.class);

	@Autowired
	private transient PersonDao dao;

	@Autowired
	private transient PersonAttributesService personAttributesService;

	@Autowired
	private transient RegistrationStatusByTermService registrationStatusByTermService;

	@Autowired
	private transient ExternalPersonService externalPersonService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Person createUserAccount(final String username,
			final Collection<GrantedAuthority> authorities) {

		Person person = null;

		if (hasAccountCreationPermission(authorities)) {
			person = new Person();
			person.setEnabled(true);
			person.setUsername(username);

			try {
				// Get the Person Attributes to create the person
				final PersonAttributesResult attr = personAttributesService
						.getAttributes(username);
				person.setSchoolId(attr.getSchoolId());
				person.setFirstName(attr.getFirstName());
				person.setLastName(attr.getLastName());
				person.setPrimaryEmailAddress(attr.getPrimaryEmailAddress());

				// try to create the person
				try {
					person = create(person);
					LOGGER.info("Successfully Created Account for {}",
							username);
				} catch (final ObjectExistsException oee) {
					person = personFromUsername(username);
				}

			} catch (final ObjectNotFoundException onfe) {
				// personAttributesService may throw this exception, if so,
				// we can't create the user.
				throw new UnableToCreateAccountException(// NOPMD
						"Unable to pull required attributes", onfe);

			} catch (final ConstraintViolationException sqlException) {
				// if we received a constraintViolationException of
				// unique_person_username, then the user might have been
				// added since we started.
				if (sqlException.getConstraintName().equals(
						"unique_person_username")) {
					LOGGER.info("Tried to add a user that was already present");
				}

			} catch (final Exception genException) {
				// This exception seems to get swallowed... trying to reveal
				// it.
				throw new UnableToCreateAccountException( // NOPMD
						"Unable to Create Account for login.", genException);
			}

		} else {
			throw new UnableToCreateAccountException( // NOPMD
					// already know the account was not found
					"Insufficient Permissions to create Account");
		}

		return person;
	}

	private boolean hasAccountCreationPermission(
			final Collection<GrantedAuthority> authorities) {
		boolean permission = ALL_AUTHENTICATED_USERS_CAN_CREATE_ACCOUNT;

		// if already true, skip permission check
		if (permission) {
			return true;
		}

		for (final GrantedAuthority auth : authorities) {
			if (auth.getAuthority().equals(PERMISSION_TO_CREATE_ACCOUNT)) {
				permission = true;
				break;
			}
		}

		return permission;
	}

	@Override
	public PagingWrapper<Person> getAll(final SortingAndPaging sAndP) {
		final PagingWrapper<Person> people = dao.getAll(sAndP);
		additionalAttribsForStudents(people);
		return people;
	}

	/**
	 * Retrieves the specified Person.
	 * 
	 * @param id
	 *            Required identifier for the Person to retrieve. Can not be
	 *            null.
	 * @exception ObjectNotFoundException
	 *                If the supplied identifier does not exist in the database
	 *                or is not {@link ObjectStatus#ACTIVE}.
	 * @return The specified Person instance.
	 */
	@Override
	public Person get(final UUID id) throws ObjectNotFoundException {
		final Person person = dao.get(id);
		return additionalAttribsForStudent(person);
	}

	@Override
	public Person load(final UUID id) {
		return dao.load(id);
	}

	@Override
	public Person getBySchoolId(final String schoolId)
			throws ObjectNotFoundException {
		try {
			return additionalAttribsForStudent(dao.getBySchoolId(schoolId));
		} catch (final ObjectNotFoundException e) {
			final ExternalPerson externalPerson = externalPersonService
					.getBySchoolId(schoolId);
			if (externalPerson == null) {
				throw new ObjectNotFoundException( // NOPMD
						"Unable to find person by schoolId: " + schoolId,
						"Person");
			}

			final Person person = new Person();
			externalPersonService.updatePersonFromExternalPerson(person,
					externalPerson);
			return additionalAttribsForStudent(person);
		}
	}

	@Override
	public Person personFromUsername(final String username)
			throws ObjectNotFoundException {

		final Person obj = dao.fromUsername(username);
		additionalAttribsForStudent(obj);

		if (null == obj) {
			throw new ObjectNotFoundException(
					"Could not find person with username: " + username,
					"Person");
		}
		return obj;
	}

	/**
	 * Creates a new Person instance based on the supplied model.
	 * 
	 * @param obj
	 *            Model instance
	 * @throws ObjectExistsException
	 *             Thrown if any of the specified data has a unique key
	 *             violation.
	 */
	@Override
	public Person create(final Person obj) {

		LOGGER.debug("Creating User {}", obj);

		if (obj.getUsername() != null) {
			final Person existing = dao.fromUsername(obj.getUsername());
			if (null != existing) {
				throw new ObjectExistsException();
			}
		}

		final Person person = dao.create(obj);

		if (LOGGER.isDebugEnabled()) {
			if (person == null) {
				LOGGER.debug("Failed to create user");
			} else {
				LOGGER.debug("User successfully created");
			}
		}

		return additionalAttribsForStudent(person);
	}

	/**
	 * @param obj
	 *            Model instance to save
	 * @see IntakeService
	 */
	@Override
	public Person save(final Person obj) throws ObjectNotFoundException {
		final Person person = dao.save(obj);
		return additionalAttribsForStudent(person);
	}

	/**
	 * Mark a Person as deleted.
	 * 
	 * Does not remove them from persistent storage, but marks their status flag
	 * to {@link ObjectStatus#INACTIVE}.
	 */
	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final Person current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.INACTIVE);
			save(current);
		}
	}

	protected void setDao(final PersonDao dao) {
		this.dao = dao;
	}

	protected void setRegistrationStatusByTermService(
			final RegistrationStatusByTermService registrationStatusByTermService) {
		this.registrationStatusByTermService = registrationStatusByTermService;
	}

	@Override
	public List<Person> peopleFromListOfIds(final List<UUID> personIds,
			final SortingAndPaging sAndP) {
		try {
			final List<Person> people = dao.getPeopleInList(personIds, sAndP);
			additionalAttribsForStudents(people);
			return people;
		} catch (final ValidationException exc) {
			return Lists.newArrayList();
		}
	}

	/**
	 * Used for Specific Report "Address Labels"
	 */
	@Override
	public List<Person> peopleFromCriteria(
			final AddressLabelSearchTO addressLabelSearchTO,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {

		final List<Person> people = dao.getPeopleByCriteria(
				addressLabelSearchTO,
				sAndP);
		additionalAttribsForStudents(people);
		return people;
	}

	@Override
	public List<Person> peopleFromSpecialServiceGroups(
			final List<UUID> specialServiceGroupIDs,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {

		final List<Person> people = dao.getPeopleBySpecialServices(
				specialServiceGroupIDs,
				sAndP);

		additionalAttribsForStudents(people);
		return people;
	}

	@Override
	public PagingWrapper<Person> getAllCoaches(final SortingAndPaging sAndP) {
		final Collection<Person> coaches = Lists.newArrayList();

		final Collection<String> coachUsernames = personAttributesService
				.getCoaches();
		for (final String coachUsername : coachUsernames) {

			Person coach = null;

			try {
				coach = personFromUsername(coachUsername);
			} catch (final ObjectNotFoundException e) {
				LOGGER.debug("Coach {} not found", coachUsername);
			}

			// Does coach exist in local SSP.person table?
			if (coach == null) {

				// Attempt to find coach in external data
				try {
					final ExternalPerson externalPerson = externalPersonService
							.getByUsername(coachUsername);

					coach = new Person(); // NOPMD
					externalPersonService.updatePersonFromExternalPerson(
							coach, externalPerson);

				} catch (final ObjectNotFoundException e) {
					LOGGER.debug("Coach {} not found in external data",
							coachUsername);
				}
			}

			if (coach != null) {
				coaches.add(coach);
			}
		}

		return new PagingWrapper<Person>(coaches);
	}

	private Iterable<Person> additionalAttribsForStudents(
			final Iterable<Person> people) {
		if (people == null) {
			return null;
		}

		for (final Person person : people) {
			additionalAttribsForStudent(person);
		}

		return people;
	}

	private Person additionalAttribsForStudent(final Person person) {
		registrationStatusByTermService
				.applyRegistrationStatusForCurrentTerm(person);
		return person;
	}

	@Override
	public void setPersonAttributesService(
			final PersonAttributesService personAttributesService) {
		this.personAttributesService = personAttributesService;
	}
}