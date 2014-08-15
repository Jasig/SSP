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
import java.util.UUID;

import javax.validation.ValidationException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * PersonProgramStatus DAO
 * 
 * @author jon.adams
 * 
 */
@Repository
public class PersonProgramStatusDao
		extends AbstractPersonAssocAuditableCrudDao<PersonProgramStatus>
		implements PersonAssocAuditableCrudDao<PersonProgramStatus> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonProgramStatusDao.class);

	/**
	 * Constructor
	 */
	public PersonProgramStatusDao() {
		super(PersonProgramStatus.class);
	}

	/**
	 * Gets the active PersonProgramStatus(es). Theoretically there should
	 * only ever be one such status, but because new statuses might be
	 * flushed to the database as a side-effect of this call, we can't
	 * guarantee there will only ever be one result without also placing
	 * awkward restrictions on when the caller can and can't add the
	 * status to the person record.
	 * 
	 * @param personId
	 *            the Person identifier
	 * @return all active (i.e. non-expired) program statuses
	 */
	@SuppressWarnings("unchecked")
	public List<PersonProgramStatus> getActive(final UUID personId) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));
		criteria.add(Restrictions.isNull("expirationDate"));
		return criteria.list();
	}

	public PagingWrapper<PersonProgramStatus> getAllForPersonIdAndProgramStatusId(
			final UUID personId, final UUID serviceReasonId,
			final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));
		criteria.add(Restrictions.eq("programStatus.id",
				serviceReasonId));
		return processCriteriaWithStatusSortingAndPaging(criteria, sAndP);
	}

	/**
	 * Same as {@link #getAllForPersonIdAndProgramStatusId(java.util.UUID, java.util.UUID, org.jasig.ssp.util.sort.SortingAndPaging)}
	 * but without the filtering and paging. Yes, this is backwards that the
	 * less restrictive search has no paging, but paging would make life
	 * unnecessarily complex for current call sites. And the likelyhood
	 * of a user having hundreds or thousand of alerts seems low.
	 *
	 * @param personId
	 * @return
	 */
	public List<PersonProgramStatus> getAllForPersonId(UUID personId) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));
		return criteria.list();
	}

	/**
	 * PersonProgramStatus often needs to update an existing row and then insert
	 * another; this gives the client a way to ensure those underlying SQL
	 * statements execute in the correct order. Ordering is important b/c triggers
	 * fire on both updates and inserts and assume those stmts run in a certain order
	 * e.g. such that the table is never in an inconsistent state w/r/t a person
	 * having multiple active statuses.
	 *
	 * @param pps
	 */
	public PersonProgramStatus saveAndFlush(PersonProgramStatus pps) {
		final UUID id = pps.getId();
		final boolean isNew = id == null;
		final PersonProgramStatus saved = super.save(pps);
		if ( isNew ) {
			//super.save() takes care of the flush for us
			return saved;
		}
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		return saved;
	}

}