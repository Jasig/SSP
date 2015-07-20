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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.PersonChallenge;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.ReferenceCounterTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.transferobject.reports.StudentChallengesTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * CRUD methods for the PersonChallenge model.
 */
@Repository
public class PersonChallengeDao extends
		AbstractPersonAssocAuditableCrudDao<PersonChallenge> implements
		PersonAssocAuditableCrudDao<PersonChallenge> {

	@Autowired
	PersonDao personDao;
	/**
	 * Constructor
	 */
	public PersonChallengeDao() {
		super(PersonChallenge.class);
	}
	
	public List<StudentChallengesTO> getStudentChallenges(PersonSearchFormTO form,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {
		
		List<UUID> ids = personDao.getStudentUUIDs(form);
		
		if(CollectionUtils.isEmpty(ids)){
			return new ArrayList<StudentChallengesTO>();
		}
		
		Criteria criteria = createCriteria();
		criteria.createAlias("person", "p");
		criteria.createAlias("p.coach", "coach");
		criteria.createAlias("p.studentType", "studentType");
		criteria.createAlias("challenge", "c");
		criteria.add(Restrictions.in("p.id", ids));
		criteria.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
		
		
		final ProjectionList projections = Projections.projectionList();
		projections.add(Projections.groupProperty("c.name").as("challengeName"));
		projections.add(Projections.groupProperty("id").as("id"));
		projections.add(Projections.groupProperty("p.firstName").as("firstName"));
		projections.add(Projections.groupProperty("p.lastName").as("lastName"));
		projections.add(Projections.groupProperty("p.schoolId").as("schoolId"));
		projections.add(Projections.groupProperty("studentType.name").as("studentType"));
		projections.add(Projections.groupProperty("coach.firstName").as("coachFirstName"));
		projections.add(Projections.groupProperty("coach.lastName").as("coachLastName"));
		criteria.setProjection(projections);
		criteria.addOrder(Order.asc("challengeName"));
		criteria.addOrder(Order.asc("lastName"));
		criteria.addOrder(Order.asc("firstName"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(
				StudentChallengesTO.class));
		return (List<StudentChallengesTO>)criteria.list();
	}
	
	public List<ReferenceCounterTO> getStudentChallengesCount(PersonSearchFormTO form) throws ObjectNotFoundException {
		List<UUID> ids = personDao.getStudentUUIDs(form);
		
		if(CollectionUtils.isEmpty(ids)){
			return new ArrayList<ReferenceCounterTO>();
		}

		Criteria criteria = createCriteria();
		criteria.createAlias("person", "p");
		criteria.createAlias("challenge", "c");
		criteria.add(Restrictions.in("p.id", ids));
		criteria.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
		
		
		final ProjectionList projections = Projections.projectionList();
		projections.add(Projections.groupProperty("c.name").as("name"));
		projections.add(Projections.countDistinct("p.id").as("count"));

		criteria.setProjection(projections);
		criteria.addOrder(Order.asc("name"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(
				ReferenceCounterTO.class));
		return (List<ReferenceCounterTO>)criteria.list();
		
	}
}