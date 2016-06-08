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
package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.Template;
import org.jasig.ssp.model.TemplateCourse;
import org.jasig.ssp.model.TemplateElectiveCourse;
import org.jasig.ssp.model.TemplateElectiveCourseElective;
import org.jasig.ssp.transferobject.reference.TemplateElectiveCourseElectiveTO;

import java.util.ArrayList;
import java.util.List;

public class TemplateCourseTO extends AbstractPlanCourseTO<Template,TemplateCourse> {

	private List<AbstractMapElectiveCourseTO> planElectiveCourseElectives;

	private String originalFormattedCourse;

	public TemplateCourseTO(TemplateCourse planCourse) {
		super();
		from(planCourse);
	}

	/**
	 * Empty constructor.
	 */
	public TemplateCourseTO() {
		super();
	}

	@Override
	public void from(TemplateCourse model) {
		super.from(model);
		this.setIsTranscript(false);
		this.setDuplicateOfTranscript(false);
	}
	public void addTemplateElectiveCourseElectives(TemplateElectiveCourse templateElectiveCourse) {

		if (templateElectiveCourse!=null) {
			planElectiveCourseElectives = new ArrayList<>();
			planElectiveCourseElectives.add(createTemplateElectiveCourseElective(templateElectiveCourse));
			for (TemplateElectiveCourseElective templateElectiveCourseElective : templateElectiveCourse.getElectiveCourseElectives()) {
				planElectiveCourseElectives.add(new TemplateElectiveCourseElectiveTO(templateElectiveCourseElective));
			}
		}
	}
	private TemplateElectiveCourseElectiveTO createTemplateElectiveCourseElective(TemplateElectiveCourse model) {
		TemplateElectiveCourseElectiveTO templateElectiveCourseElective = new TemplateElectiveCourseElectiveTO();
		templateElectiveCourseElective.setId(model.getId());
		if (model.getCreatedBy() != null) {
			templateElectiveCourseElective.setCreatedBy(new PersonLiteTO(model.getCreatedBy().getId(),model.getCreatedBy().getFirstName(),model.getCreatedBy().getLastName()));
		}

		if (model.getModifiedBy() != null) {
			templateElectiveCourseElective.setModifiedBy(new PersonLiteTO(model.getModifiedBy().getId(),model.getModifiedBy().getFirstName(),model.getModifiedBy().getLastName()));
		}

		templateElectiveCourseElective.setCreatedDate(model.getCreatedDate());
		templateElectiveCourseElective.setModifiedDate(model.getModifiedDate());
		templateElectiveCourseElective.setObjectStatus(model.getObjectStatus());
		templateElectiveCourseElective.setCourseCode(model.getCourseCode());
		templateElectiveCourseElective.setCourseDescription(model.getCourseDescription());
		templateElectiveCourseElective.setCourseTitle(model.getCourseTitle());
		templateElectiveCourseElective.setFormattedCourse(model.getFormattedCourse());
		templateElectiveCourseElective.setCreditHours(model.getCreditHours());

		return templateElectiveCourseElective;
	}

	public List<AbstractMapElectiveCourseTO> getPlanElectiveCourseElectives() {
		return planElectiveCourseElectives;
	}

	public void setPlanElectiveCourseElectives(List<AbstractMapElectiveCourseTO> planElectiveCourseElectives) {
		this.planElectiveCourseElectives = planElectiveCourseElectives;
	}

	public String getOriginalFormattedCourse() {
		return originalFormattedCourse;
	}

	public void setOriginalFormattedCourse(String originalFormattedCourse) {
		this.originalFormattedCourse = originalFormattedCourse;
	}
}
