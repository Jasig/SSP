package org.jasig.ssp.dao;

import java.security.InvalidParameterException;
import java.util.Locale;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * PersonSearch DAO
 */
@Repository
public class PersonSearchDao extends AbstractDao<Person> {

	public PersonSearchDao() {
		super(Person.class);
	}

	@Autowired
	private transient ConfigService configService;

	public PagingWrapper<Person> searchBy(
			final ProgramStatus programStatus, final Boolean outsideCaseload,
			final String searchTerm, final Person advisor,
			final SortingAndPaging sAndP) {

		final Criteria query = createCriteria();

		if (programStatus != null) {
			query.createAlias("programStatuses", "personProgramStatus")
					.add(Restrictions.eq("personProgramStatus.programStatus",
							programStatus));
		}

		if (Boolean.FALSE.equals(outsideCaseload)) {
			query.add(Restrictions.eq("coach", advisor));
		}

		// searchTerm : Can be firstName, lastName, studentId or firstName + ' '
		// + lastName
		final Disjunction terms = Restrictions.disjunction();
		if (searchTerm == null) {
			throw new InvalidParameterException("search term must be specified");
		} else {
			final String searchTermLowercase = searchTerm.toLowerCase(Locale
					.getDefault());
			terms.add(Restrictions.ilike("firstName", searchTermLowercase,
					MatchMode.ANYWHERE));
			terms.add(Restrictions.ilike("lastName", searchTermLowercase,
					MatchMode.ANYWHERE));
			terms.add(Restrictions.ilike("schoolId", searchTermLowercase,
					MatchMode.ANYWHERE));

			terms.add(Restrictions
					.sqlRestriction(
							"lower({alias}.first_name) "
									+ configService.getDatabaseConcatOperator()
									+ " ' ' "
									+ configService.getDatabaseConcatOperator()
									+ " lower({alias}.last_name) like ? ",
							searchTermLowercase, new StringType()));

		}
		query.add(terms);

		return processCriteriaWithPaging(query, sAndP);
	}
}