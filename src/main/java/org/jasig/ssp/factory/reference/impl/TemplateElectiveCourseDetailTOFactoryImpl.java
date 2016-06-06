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

import com.google.common.collect.Lists;
import org.jasig.ssp.dao.reference.TemplateElectiveCourseDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.reference.TemplateElectiveCourseDetailTOFactory;
import org.jasig.ssp.model.TemplateCourse;
import org.jasig.ssp.model.TemplateElectiveCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.TemplateElectiveCourseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TemplateElectiveCourseDetailTOFactoryImpl extends
		AbstractAuditableTOFactory<TemplateElectiveCourseTO, TemplateElectiveCourse>
		implements TemplateElectiveCourseDetailTOFactory {

	public TemplateElectiveCourseDetailTOFactoryImpl() {
		super(TemplateElectiveCourseTO.class, TemplateElectiveCourse.class);
	}

	@Autowired
	private transient TemplateElectiveCourseDao dao;

	@Override
	protected TemplateElectiveCourseDao getDao() {
		return dao;
	}

	@Override
	public TemplateElectiveCourse from(final TemplateElectiveCourseTO tObject)
			throws ObjectNotFoundException {
		final TemplateElectiveCourse model = super.from(tObject);

//		model.setSortOrder(tObject.getSortOrder());

		return model;
	}

	public TemplateElectiveCourseTO from(TemplateCourse model) {
		TemplateElectiveCourseTO toReturn = new TemplateElectiveCourseTO();
		toReturn.setCourseCode(model.getCourseCode());
		toReturn.setCourseDescription(model.getCourseDescription());
		toReturn.setCourseTitle(model.getCourseTitle());
		toReturn.setFormattedCourse(model.getFormattedCourse());
		toReturn.setCreditHours(model.getCreditHours());
		toReturn.setId(model.getId());

		return toReturn;
	}


	public List<TemplateElectiveCourseTO> asTOList(final List<TemplateCourse> models) {
		final List<TemplateElectiveCourseTO> tos = Lists.newArrayList();

		if ((models != null) && !models.isEmpty()) {
			for (TemplateCourse model : models) {
				tos.add(from(model));
			}
		}

		return tos;
	}

}
