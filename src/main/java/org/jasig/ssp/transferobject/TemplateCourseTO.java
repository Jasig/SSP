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

import org.jasig.ssp.model.AbstractMapElectiveCourse;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.model.TemplateCourse;
import org.jasig.ssp.model.TemplateElectiveCourse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TemplateCourseTO extends AbstractPlanCourseTO<Template,TemplateCourse> {


	public TemplateCourseTO(TemplateCourse planCourse) {
		super();
		from(planCourse);
	}
	private UUID planElectiveCourseId;

	private List<AbstractMapElectiveCourseTO> planElectiveCourseElectives;

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

		TemplateElectiveCourse templateElectiveCourse = model.getTemplateElectiveCourse();
		if (templateElectiveCourse!=null) {
			planElectiveCourseId = templateElectiveCourse.getId();
			planElectiveCourseElectives = new ArrayList<>();
			planElectiveCourseElectives.add(new PlanElectiveCourseElectiveTO(templateElectiveCourse));
			for (AbstractMapElectiveCourse electiveCourseElective : templateElectiveCourse.getElectiveCourseElectives()) {
				planElectiveCourseElectives.add(new PlanElectiveCourseElectiveTO(electiveCourseElective));
			}
		}
	}

	public UUID getPlanElectiveCourseId() {
		return planElectiveCourseId;
	}

	public void setPlanElectiveCourseId(UUID planElectiveCourseId) {
		this.planElectiveCourseId = planElectiveCourseId;
	}

	public List<AbstractMapElectiveCourseTO> getPlanElectiveCourseElectives() {
		return planElectiveCourseElectives;
	}

	public void setPlanElectiveCourseElectives(List<AbstractMapElectiveCourseTO> planElectiveCourseElectives) {
		this.planElectiveCourseElectives = planElectiveCourseElectives;
	}
}
