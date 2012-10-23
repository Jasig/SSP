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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.management.Query;
import javax.validation.constraints.NotNull;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.reports.CaseloadReportController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CaseloadDao extends AbstractDao<Person> {

	public CaseloadDao() {
		super(Person.class);
	}

	@SuppressWarnings("unchecked")
	public PagingWrapper<CaseloadRecord> caseLoadFor(
			final ProgramStatus programStatus, @NotNull final Person coach,
			final SortingAndPaging sAndP) {

		// This creation of the query is order sensitive as 2 queries are run
		// with the same restrictions. The first query simply runs the query to
		// find a count of the records. The second query returns the row data.
		// protected Criteria createCriteria() {
		// return sessionFactory.getCurrentSession().createCriteria(
		// this.persistentClass);
		// }
		final Criteria query = this.createCriteria();

		// Restrict by program status if provided
		if (programStatus != null) {
			final Criteria subquery = query.createAlias("programStatuses",
					"personProgramStatus");
			subquery.add(Restrictions.or(Restrictions
					.isNull("personProgramStatus.expirationDate"), Restrictions
					.ge("personProgramStatus.expirationDate", new Date())));
			subquery.add(Restrictions.eq("personProgramStatus.programStatus",
					programStatus));
		}

		// restrict to coach
		query.add(Restrictions.eq("coach", coach));

		// item count
		Long totalRows = 0L;
		if ((sAndP != null) && sAndP.isPaged()) {
			totalRows = (Long) query.setProjection(Projections.rowCount())
					.uniqueResult();
		}

		// clear the row count projection
		query.setProjection(null);

		//
		// Add Properties to return in the case load
		//
		// Set Columns to Return: id, firstName, middleName, lastName,
		// schoolId
		final ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id").as("personId"));
		projections.add(Projections.property("firstName").as("firstName"));
		projections.add(Projections.property("middleName").as("middleName"));
		projections.add(Projections.property("lastName").as("lastName"));
		projections.add(Projections.property("schoolId").as("schoolId"));
		projections.add(Projections.property("studentIntakeCompleteDate").as(
				"studentIntakeCompleteDate"));

		// Join to Student Type
		query.createAlias("studentType", "studentType",
				JoinType.LEFT_OUTER_JOIN);
		// add StudentTypeName Column
		projections.add(Projections.property("studentType.name").as(
				"studentTypeName"));

		query.setProjection(projections);

		query.setResultTransformer(new AliasToBeanResultTransformer(
				CaseloadRecord.class));

		// Add Paging
		if (sAndP != null) {
			sAndP.addAll(query);
		}

		return new PagingWrapper<CaseloadRecord>(totalRows, query.list());
	}

	@SuppressWarnings("unchecked")
	public Long caseLoadCountFor(final ProgramStatus programStatus,
			@NotNull final Person coach, List<UUID> studentTypeIds,
			Date dateFrom, Date dateTo) {

		final Criteria query = createCriteria();

		// Restrict by program status if provided
		if (programStatus != null) {
			final Criteria subquery = query.createAlias("programStatuses",
					"personProgramStatus");

			if (dateFrom != null) {
				subquery.add(Restrictions.ge(
						"personProgramStatus.effectiveDate", dateFrom));
			}
			if (dateTo != null) {
				subquery.add(Restrictions.le(
						"personProgramStatus.effectiveDate", dateTo));
			}

			subquery.add(Restrictions.or(Restrictions
					.isNull("personProgramStatus.expirationDate"), Restrictions
					.ge("personProgramStatus.expirationDate", new Date())));

			subquery.add(Restrictions.eq("personProgramStatus.programStatus",
					programStatus));
		}

		// restrict to coach
		query.add(Restrictions.eq("coach", coach));

		// add possible studentTypeId Check
		if (studentTypeIds != null && !studentTypeIds.isEmpty()) {
			query.add(Restrictions.in("studentType.id", studentTypeIds));
		}

		// item count
		Long totalRows = (Long) query.setProjection(Projections.rowCount())
				.uniqueResult();

		return totalRows;
	}

}