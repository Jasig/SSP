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
package org.jasig.ssp.dao.reference;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AbstractAuditableCrudDao;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.TemplateElectiveCourse;
import org.jasig.ssp.model.TemplateElectiveCourseElective;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access class for the TemplateElectiveCourse reference entity.
 */
@Repository
public class TemplateElectiveCourseElectiveDao extends
		AbstractAuditableCrudDao<TemplateElectiveCourseElective>
		implements AuditableCrudDao<TemplateElectiveCourseElective> {

	public TemplateElectiveCourseElectiveDao() {
		super(TemplateElectiveCourseElective.class);
	}

	public List<TemplateElectiveCourseElective> getAllElectivesForElectiveCourse(TemplateElectiveCourse templateElectiveCourse) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("templateElectiveCourse", templateElectiveCourse));
		criteria.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
		criteria.addOrder(Order.asc("formattedCourse"));
		return criteria.list();
	}

	@Override
	public void delete(TemplateElectiveCourseElective templateElectiveCourseElective) {
		super.delete(templateElectiveCourseElective);
		sessionFactory.getCurrentSession().flush();
	}
}