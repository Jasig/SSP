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
package org.jasig.ssp.dao;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public class PersonSpecialServiceGroupDao
		extends AbstractPersonAssocAuditableCrudDao<PersonSpecialServiceGroup>
		implements PersonAssocAuditableCrudDao<PersonSpecialServiceGroup> {

	/**
	 * Constructor
	 */
	public PersonSpecialServiceGroupDao() {
		super(PersonSpecialServiceGroup.class);
	}

	public PagingWrapper<PersonSpecialServiceGroup> getAllForPersonIdAndSpecialServiceGroupId(
			final UUID personId, final UUID specialServiceGroupId,
			final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));
		criteria.add(Restrictions.eq("specialServiceGroup.id",
				specialServiceGroupId));
		return processCriteriaWithStatusSortingAndPaging(criteria, sAndP);
	}

	public List<String> getAllCodesForPersonId(final UUID personId) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));
        criteria.setProjection(Projections.property("ssg.code"));
		criteria.createAlias("specialServiceGroup", "ssg");

        return criteria.list();
	}

    public void deleteAllForPerson(final UUID personId) {
        final Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("person.id", personId));
        final List<PersonSpecialServiceGroup> personSpecialServiceGroups = criteria.list();

        if (CollectionUtils.isNotEmpty(personSpecialServiceGroups)) {
            for (PersonSpecialServiceGroup personSpecialServiceGroup : personSpecialServiceGroups) {
                this.delete(personSpecialServiceGroup);
            }
            sessionFactory.getCurrentSession().flush();
        } else {
            return;
        }
    }

    public void deleteForPersonByCode(final String code, final UUID id) {
        final Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("person.id", id));
        criteria.add(Restrictions.eq("ssg.code", code));
        criteria.createAlias("specialServiceGroup", "ssg");
        final PersonSpecialServiceGroup personSpecialServiceGroup = (PersonSpecialServiceGroup) criteria.uniqueResult();

        if (personSpecialServiceGroup != null) {
            this.delete(personSpecialServiceGroup);
            sessionFactory.getCurrentSession().flush();
        } else {
            return;
        }
    }
}