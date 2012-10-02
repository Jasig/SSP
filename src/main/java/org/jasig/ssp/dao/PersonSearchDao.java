/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao;

import java.util.List;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
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

import com.google.common.collect.Lists;

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

	/**
	 * Search people by the specified terms.
	 * 
	 * @param programStatus
	 *            program status filter
	 * @param outsideCaseload
	 *            false allows searches without checking the Coach (advisor)
	 *            property; defaults to true
	 * @param searchTerm
	 *            Search term that search first and last name and school ID;
	 *            required
	 * @param advisor
	 *            required if outsideCaseload is not {@link Boolean#FALSE}.
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return List of people that match the specified filters
	 */
	public PagingWrapper<Person> searchBy(
			@NotNull final ProgramStatus programStatus,
			final Boolean outsideCaseload, @NotNull final String searchTerm,
			final Person advisor, final SortingAndPaging sAndP) {

		if (StringUtils.isBlank(searchTerm)) {
			throw new IllegalArgumentException("search term must be specified");
		}

		final Criteria query = createCriteria();

		query.createAlias("programStatuses", "personProgramStatus");

		if (programStatus != null) {
			query.add(Restrictions.eq("personProgramStatus.programStatus",
					programStatus));
		}

		if ((sAndP != null) && sAndP.isFilteredByStatus()) {
			query.add(Restrictions
					.isNull("personProgramStatus.expirationDate"));
		}

		if (Boolean.FALSE.equals(outsideCaseload)) {
			query.add(Restrictions.eq("coach", advisor));
		}

		// searchTerm : Can be firstName, lastName, studentId or firstName + ' '
		// + lastName
		final Disjunction terms = Restrictions.disjunction();

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

		query.add(terms);

		// eager load program status
		query.setFetchMode("personProgramStatus", FetchMode.JOIN);
		query.setFetchMode("personProgramStatus.programStatus", FetchMode.JOIN);

		final PagingWrapper<Object[]> results = processCriteriaWithSortingAndPaging(
				query, sAndP, true);

		final List<Person> people = Lists.newArrayList();
		for (Object[] personAndProgramStatus : results) {
			if ((personAndProgramStatus != null)
					&& (personAndProgramStatus.length > 0)) {
				if (personAndProgramStatus[0] instanceof Person) {
					people.add((Person) personAndProgramStatus[0]);
				} else if (personAndProgramStatus[1] instanceof Person) {
					people.add((Person) personAndProgramStatus[1]);
				}
			}
		}

		return new PagingWrapper<Person>(results.getResults(), people);
	}
}