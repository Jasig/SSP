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

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.TemplateCourse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Data access class for the TemplateElectiveCourse reference entity.
 */
@Repository
public class TemplateCourseDao extends
		AbstractAuditableCrudDao<TemplateCourse>
		implements AuditableCrudDao<TemplateCourse> {

	public TemplateCourseDao() {
		super(TemplateCourse.class);
	}

	public List<TemplateCourse> getAllCoursesForTemplate (final UUID id) {
		List<TemplateCourse> list = createCriteria()
				.add(Restrictions.eq("template.id", id))
				.list();

		//Loop through the list to get a distinct set of formatted course for a template.  At this point, we don't care
		// which templateCourse.id is used as long it is unique
		List<UUID> uuidList = new ArrayList<>();
		List<String> formattedCourseList = new ArrayList<>();
		for (TemplateCourse templateCourse : list) {
			if (!formattedCourseList.contains(templateCourse.getFormattedCourse())) {
				uuidList.add(templateCourse.getId());
				formattedCourseList.add(templateCourse.getFormattedCourse());
			}
		}
		return createCriteria().add(Restrictions.in("id", uuidList)).addOrder(Order.asc("formattedCourse")).list();
	}
}