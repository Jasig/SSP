package org.jasig.ssp.dao.external;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the External Person entity
 */
@Repository
public class ExternalPersonDao extends AbstractExternalDataDao<ExternalPerson> {

	public ExternalPersonDao() {
		super(ExternalPerson.class);
	}

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param schoolId
	 *            the schoolId value
	 * @return The specified instance if found
	 * @throws ObjectNotFoundException
	 *             If object was not found.
	 */
	public ExternalPerson getBySchoolId(@NotNull final String schoolId)
			throws ObjectNotFoundException {
		if (StringUtils.isBlank(schoolId)) {
			throw new ObjectNotFoundException(schoolId,
					ExternalPerson.class.getName());
		}

		final ExternalPerson obj = (ExternalPerson) createCriteria()
				.add(Restrictions.eq("schoolId", schoolId)).uniqueResult();

		if (obj == null) {
			throw new ObjectNotFoundException(schoolId,
					ExternalPerson.class.getName());
		}

		return obj;
	}
	
	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param username
	 *            the username value
	 * @return The specified instance if found
	 * @throws ObjectNotFoundException
	 *             If object was not found.
	 */
	public ExternalPerson getByUsername(@NotNull final String username)
			throws ObjectNotFoundException {
		if (StringUtils.isBlank(username)) {
			throw new ObjectNotFoundException(username,
					ExternalPerson.class.getName());
		}

		final ExternalPerson obj = (ExternalPerson) createCriteria()
				.add(Restrictions.eq("username", username)).uniqueResult();

		if (obj == null) {
			throw new ObjectNotFoundException(username,
					ExternalPerson.class.getName());
		}

		return obj;
	}

	public PagingWrapper<ExternalPerson> getBySchoolIds(
			@NotNull final Collection<String> schoolIds,
			final SortingAndPaging sAndP) {

		final Criteria query = createCriteria()
				.add(Restrictions.in("schoolId", schoolIds));

		return processCriteriaWithSortingAndPaging(query, sAndP, false);
	}
}
