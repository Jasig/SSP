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
package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.PlanElectiveCourseDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.reference.PlanElectiveCourseTOFactory;
import org.jasig.ssp.model.PlanCourse;
import org.jasig.ssp.model.PlanElectiveCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PlanElectiveCourseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PlanElectiveCourseTOFactoryImpl extends
		AbstractAuditableTOFactory<PlanElectiveCourseTO, PlanElectiveCourse>
		implements PlanElectiveCourseTOFactory {

	public PlanElectiveCourseTOFactoryImpl() {
		super(PlanElectiveCourseTO.class, PlanElectiveCourse.class);
	}

	@Autowired
	private transient PlanElectiveCourseDao dao;

	@Override
	protected PlanElectiveCourseDao getDao() {
		return dao;
	}

	@Override
	public PlanElectiveCourse from(final PlanElectiveCourseTO tObject)
			throws ObjectNotFoundException {
		final PlanElectiveCourse model = super.from(tObject);

//		model.setSortOrder(tObject.getSortOrder());

		return model;
	}

	public PlanElectiveCourseTO from(PlanCourse model) {
		PlanElectiveCourseTO toReturn = new PlanElectiveCourseTO();
		toReturn.setCourseCode(model.getCourseCode());
		toReturn.setCourseDescription(model.getCourseDescription());
		toReturn.setCourseTitle(model.getCourseTitle());
		toReturn.setFormattedCourse(model.getFormattedCourse());
		toReturn.setCreditHours(model.getCreditHours());
		toReturn.setId(model.getId());

		return toReturn;
	}
}
