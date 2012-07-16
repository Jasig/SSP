package org.jasig.ssp.dao.external;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the RegistrationStatusByTerm reference entity.
 */
@Repository
public class RegistrationStatusByTermDao extends
		AbstractExternalDataDao<RegistrationStatusByTerm> {

	public RegistrationStatusByTermDao() {
		super(RegistrationStatusByTerm.class);
	}

	public RegistrationStatusByTerm getForTerm(final Person person,
			final Term term) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("schoolId", person.getSchoolId()));
		query.add(Restrictions.eq("termCode", term.getCode()));
		query.add(Restrictions.gt("registeredCourseCount", 0));
		return (RegistrationStatusByTerm) query.uniqueResult();
	}

	public PagingWrapper<RegistrationStatusByTerm> getAllForPerson(
			final Person person, final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("schoolId", person.getSchoolId()));
		query.add(Restrictions.gt("registeredCourseCount", 0));

		return processCriteriaWithSortingAndPaging(query, sAndP);
	}
}
