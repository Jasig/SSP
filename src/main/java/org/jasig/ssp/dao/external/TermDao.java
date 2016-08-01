/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao.external;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.DateTimeUtils;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Data access class for the Term reference entity.
 */
@Repository
public class TermDao extends AbstractExternalReferenceDataDao<Term> {

	public TermDao() {
		super(Term.class);
	}

	public Term getCurrentTerm() throws ObjectNotFoundException {
		final java.util.Date now = DateTimeUtils.midnight();
		
		final Criteria query = createCriteria();		
		query.add(Restrictions.ge("endDate", now));		
		query.addOrder(Order.asc("startDate"));
		query.setMaxResults(1);

		@SuppressWarnings("unchecked")
		final List <Term> term = (List<Term>) query.list();

		if (term == null || term.isEmpty()) {
			throw new ObjectNotFoundException("Current Term not Defined", "Term");
		} else {
			return term.get(0);
		}
	}

	public Term getNextTerm(java.util.Date termEndDate) throws ObjectNotFoundException {
		final Criteria query = createCriteria();
		query.add(Restrictions.ge("startDate", termEndDate));
		query.addOrder(Order.asc("startDate"));
		query.setMaxResults(1);

		@SuppressWarnings("unchecked")
		final List <Term> term = (List<Term>) query.list();

		if (term == null || term.isEmpty()) {
			throw new ObjectNotFoundException("Next Term not Defined for Date: " + termEndDate.toString(), "Term");
		} else {
			return term.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Term> getCurrentAndFutureTerms() throws ObjectNotFoundException {
		final java.util.Date now = DateTimeUtils.midnight();

		final Criteria query = createCriteria();
		query.add(Restrictions.ge("endDate", now));

		final List<Term> terms = (List<Term>) query.list();

		if (terms == null || terms.isEmpty()) {
			throw new ObjectNotFoundException("Could not find any terms", "Term");
		} else {
			return terms;
		}
	}
	
	@Override
	public PagingWrapper<Term> getAll(final SortingAndPaging sAndP) {
		return processCriteriaWithSortingAndPaging(createCriteria(), sAndP, false);
	}

	@SuppressWarnings("unchecked")
	public List<Term> facetSearch(String tag, String programCode) {
		final Query hqlQuery = createHqlQuery(buildProgramSearchQuery(tag, programCode));
		buildProgramSearchParamList(tag, programCode, hqlQuery);

        return hqlQuery.list();
	}

	private void buildProgramSearchParamList(String tag, String programCode,Query hqlQuery) {

		hqlQuery.setDate("now", DateTimeUtils.midnight());
		if (!StringUtils.isEmpty(programCode)) {
			hqlQuery.setString("programCode", programCode);
		}

		if (!StringUtils.isEmpty(tag)) {
			hqlQuery.setString("tag", tag);
		}			
	}

	private String buildProgramSearchQuery(String tag, String programCode) {
		final StringBuilder query = new StringBuilder();
		query.append(" select distinct et from Term et , ExternalCourseTerm ect , ExternalCourse ec ");

        if (!StringUtils.isEmpty(tag)) {
			query.append(" ,ExternalCourseTag ectg ");
		}

		if (!StringUtils.isEmpty(programCode)) {
			query.append(" ,ExternalCourseProgram ecp ");
		}
		query.append(" where ");
		query.append(" et.code = ect.termCode ");
		query.append(" and ect.courseCode = ec.code  ");
		query.append(" and et.endDate >= :now  ");

        if (!StringUtils.isEmpty(tag)) {
			query.append(" and ec.code = ectg.courseCode ");
			query.append(" and ectg.tag = :tag ");
		}

		if (!StringUtils.isEmpty(programCode)) {
			query.append(" and ec.code = ecp.courseCode ");
			query.append(" and ecp.programCode = :programCode  ");
		}
		query.append(" order by et.startDate ");

        return query.toString();
	}

	@SuppressWarnings("unchecked")
	public List<Term> getTermsWithRegistrationWindowOpenIfAny() {
		final String query = "from Term term where term.registrationStartDate <= current_date() and current_date() <= term.registrationEndDate";

        return createHqlQuery(query).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Term> getTermsByCodes(List<String> codes) {
		if (codes == null || codes.size() == 0) {
            return new ArrayList<Term>();
        }
		
		final String hql = "from Term term where term.code in :codes";
		final Query query = createHqlQuery(hql);
		query.setParameterList("codes", codes);

        return query.list();
	}

    public List<Term> getTermsByEndDateRange(Date dateFrom, Date dateTo) {
        if (dateFrom == null && dateTo == null) {
            return Lists.newArrayList();
        }

        final Criteria query = createCriteria();
        if (dateFrom != null) {
            query.add(Restrictions.ge("endDate", DateTimeUtils.midnightOn(dateFrom)));
        }

        if (dateTo != null) {
            query.add(Restrictions.le("endDate", DateTimeUtils.midnightOn(dateTo)));
        }

        query.addOrder(Order.asc("startDate"));

        return query.list();
    }

    public List<String> getTermCodesByStartDateEndDateRange(Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {
            return Lists.newArrayList();
        }

        final Criteria query = createCriteria();
        if (startDate != null) {
            query.add(Restrictions.gt("endDate", DateTimeUtils.midnightOn(startDate)));
        }

        if (endDate != null) {
            query.add(Restrictions.lt("startDate", DateTimeUtils.midnightOn(endDate)));
        }

        query.addOrder(Order.asc("startDate"));
        query.setProjection((Projections.property("code")));

        return query.list();
    }
}