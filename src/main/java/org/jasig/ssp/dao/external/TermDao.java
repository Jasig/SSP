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
package org.jasig.ssp.dao.external;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the Term reference entity.
 */
@Repository
public class TermDao extends AbstractExternalReferenceDataDao<Term> {

	public TermDao() {
		super(Term.class);
	}

	public Term getCurrentTerm() throws ObjectNotFoundException {
		final Date now = new Date();

		final Criteria query = createCriteria();
		query.add(Restrictions.lt("startDate", now));
		query.add(Restrictions.gt("endDate", now));

		final Term term = (Term) query.uniqueResult();

		if (term == null) {
			throw new ObjectNotFoundException("Current Term not Defined",
					"Term");
		} else {
			return term;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Term> getCurrentAndFutureTerms() throws ObjectNotFoundException {
		final Date now = new Date();

		final Criteria query = createCriteria();
		query.add(Restrictions.gt("endDate", now));

		final List<Term> terms = (List<Term>) query.list();

		if (terms == null || terms.isEmpty()) {
			throw new ObjectNotFoundException("Could not find any terms",
					"Term");
		} else {
			return terms;
		}
	}
	
	@Override
	public PagingWrapper<Term> getAll(final SortingAndPaging sAndP) {
		return processCriteriaWithSortingAndPaging(createCriteria(),
				sAndP, false);
	}
}
