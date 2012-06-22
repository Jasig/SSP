package org.jasig.ssp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.tool.IntakeService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author codynet
 * 
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	@Autowired
	private transient PersonDao dao;

	@Override
	public PagingWrapper<Person> getAll(final SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
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
		return dao.get(id);
	}

	@Override
	public Person getByStudentId(final String studentId)
			throws ObjectNotFoundException {
		return dao.getByStudentId(studentId);
	}

	@Override
	public Person personFromUsername(final String username)
			throws ObjectNotFoundException {
		final Person obj = dao.fromUsername(username);
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
	 */
	@Override
	public Person create(final Person obj) {
		return dao.save(obj);
	}

	/**
	 * @param obj
	 *            Model instance to save
	 * @see IntakeService
	 */
	@Override
	public Person save(final Person obj) throws ObjectNotFoundException {
		return dao.save(obj);
	}

	/**
	 * Mark a Person as deleted.
	 * 
	 * Does not remove them from persistent storage, but marks their status flag
	 * to {@link ObjectStatus#DELETED}.
	 */
	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final Person current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(final PersonDao dao) {
		this.dao = dao;
	}

	@Override
	public Person personFromUserId(final String userId)
			throws ObjectNotFoundException {
		return dao.fromUserId(userId);
	}

	@Override
	public List<Person> peopleFromListOfIds(final List<UUID> personIds,
			final SortingAndPaging sAndP) {
		return dao.getPeopleInList(personIds, sAndP);
	}

	/**
	 * Used for Specific Report "Address Labels"
	 */
	@Override
	public List<Person> peopleFromCriteria(final Date intakeDatefrom,
			final Date intakeDateTo,
			final String homeDepartment,
			final String coachId,
			final String programStatus,
			final String specialServiceGroupId,
			final String referralSourcesId,
			final String anticipatedStartTerm,
			final Integer anticipatedStartYear,
			final String studentTypeId,
			final Date registrationTerm,
			final Date registrationYear,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {

		// TODO: use a TO here
		return dao.getPeopleByCriteria(
				intakeDatefrom,
				intakeDateTo,
				homeDepartment,
				coachId,
				programStatus,
				specialServiceGroupId,
				referralSourcesId,
				anticipatedStartTerm,
				anticipatedStartYear,
				studentTypeId,
				registrationTerm,
				registrationYear,
				sAndP);
	}
}