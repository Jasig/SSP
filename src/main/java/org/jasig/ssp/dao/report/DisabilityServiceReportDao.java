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
package org.jasig.ssp.dao.report;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.transferobject.reports.DisabilityServicesReportTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public class DisabilityServiceReportDao extends PersonDao {

	public DisabilityServiceReportDao() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<DisabilityServicesReportTO> getDisabilityReport(PersonSearchFormTO form,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {
		Criteria criteria = this.setBasicSearchCriteria(createCriteria(sAndP),  form);
		criteria.add(Restrictions.isNotNull("studentType"));
		
		// item count
		Long totalRows = 0L;
		if ((sAndP != null) && sAndP.isPaged()) {
			totalRows = (Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult();
		}

		// clear the row count projection
		criteria.setProjection(null);

				
		// don't bring back any non-students, there will likely be a better way
		// to do this later
		
		final ProjectionList projections = Projections.projectionList();
		
		criteria.setProjection(projections);
		addBasicStudentProperties(projections, criteria);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(
				DisabilityServicesReportTO.class));

		// Add Paging
		if (sAndP != null) {
			sAndP.addAll(criteria);
		}

		return new PagingWrapper<DisabilityServicesReportTO>(totalRows, criteria.list());

	}
	
	private ProjectionList addBasicStudentProperties(ProjectionList projections, Criteria criteria){
		
		projections.add(Projections.property("id").as("personId"));
		projections.add(Projections.property("firstName").as("firstName"));
		projections.add(Projections.property("middleName").as("middleName"));
		projections.add(Projections.property("lastName").as("lastName"));
		projections.add(Projections.property("schoolId").as("schoolId"));
		projections.add(Projections.property("primaryEmailAddress").as("primaryEmailAddress"));
		projections.add(Projections.property("secondaryEmailAddress").as("secondaryEmailAddress"));

		// Join to Student Type
		criteria.createAlias("studentType", "studentType",
				JoinType.LEFT_OUTER_JOIN);
		// add StudentTypeName Column
		projections.add(Projections.property("studentType.name").as("studentType"));

		Dialect dialect = ((SessionFactoryImplementor) sessionFactory).getDialect();
		if ( dialect instanceof SQLServerDialect) {
			// sql server requires all these to part of the grouping
			projections.add(Projections.groupProperty("c.lastName").as("coachLastName"))
					.add(Projections.groupProperty("c.firstName").as("coachFirstName"))
					.add(Projections.groupProperty("c.middleName").as("coachMiddleName"))
					.add(Projections.groupProperty("c.schoolId").as("coachSchoolId"))
					.add(Projections.groupProperty("c.username").as("coachUsername"));
		} else {
			// other dbs (postgres) don't need these in the grouping
			projections.add(Projections.property("c.lastName").as("coachLastName"))
					.add(Projections.property("c.firstName").as("coachFirstName"))
					.add(Projections.property("c.middleName").as("coachMiddleName"))
					.add(Projections.property("c.schoolId").as("coachSchoolId"))
					.add(Projections.property("c.username").as("coachUsername"));
		}
		return projections;
	}
}
