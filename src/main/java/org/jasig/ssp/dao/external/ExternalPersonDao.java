package org.jasig.ssp.dao.external;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the Term reference entity.
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

	public PagingWrapper<ExternalPerson> pullUpdatedUsers(
			final SortingAndPaging sAndP) {

		return new PagingWrapper<ExternalPerson>(0, null);
	}
}
