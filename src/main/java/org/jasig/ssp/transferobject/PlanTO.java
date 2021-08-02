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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.PlanCourse;
import org.jasig.ssp.model.PlanElectiveCourse;
import org.jasig.ssp.model.TermNote;
import java.util.ArrayList;
import java.util.List;


/**
 */
public class PlanTO extends AbstractPlanTO<Plan> {

	private String personId;

	private String basedOnTemplateId;

	private List<PlanCourseTO> planCourses = new ArrayList<PlanCourseTO>();

	private List<PlanElectiveCourseTO> planElectiveCourses= new ArrayList<PlanElectiveCourseTO>();
	/**
	 * Empty constructor.
	 */
	public PlanTO() {
		super();
	}
	/**
	 * Empty constructor.
	 * @param model the plan model object
	 */
	public PlanTO(Plan model) {
		super();
		from(model);
	}

	// This is totally wrong just so that validation can take place without hitting plan is modifi
	public void from(Plan model) {
		super.from(model);
		this.setPersonId(model.getPerson().getId().toString());

        if (model.getTemplateBasedOn() != null) {
            this.setBasedOnTemplateId(model.getTemplateBasedOn().getId().toString());
        }

        List<PlanCourse> planCourses = model.getPlanCourses();
		for (PlanCourse planCourse : planCourses) {
			PlanCourseTO courseTO = new PlanCourseTO(planCourse);
			if (null!=planCourse.getOriginalFormattedCourse()) {
				PlanElectiveCourse planElectiveCourse = (PlanElectiveCourse)findPlanElectiveCourse(model.getPlanElectiveCourses(), planCourse.getOriginalFormattedCourse());
				courseTO.addPlanElectiveCourseElectives(planElectiveCourse);
			}
			this.getPlanCourses().add(courseTO);
		}

		List<TermNote> termNotes = model.getTermNotes();
		for (TermNote termNote : termNotes) {
			TermNoteTO termNoteTO = new TermNoteTO(termNote);
			this.getTermNotes().add(termNoteTO);
		}

		List<PlanElectiveCourse> planElectiveCourses = model.getPlanElectiveCourses();
		for (PlanElectiveCourse planElectiveCourse : planElectiveCourses) {
			PlanElectiveCourseTO planElectiveCourseTO = new PlanElectiveCourseTO(planElectiveCourse);
			this.getPlanElectiveCourses().add(planElectiveCourseTO);
		}
	}

	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public List<PlanCourseTO> getPlanCourses() {
		return planCourses;
	}
	public void setPlanCourses(List<PlanCourseTO> planCourses) {
		this.planCourses = planCourses;
	}

	@Override
	@JsonIgnore
	public List<PlanCourseTO> getCourses() {
		return planCourses;
	}

	public List<PlanElectiveCourseTO> getPlanElectiveCourses() {
		return planElectiveCourses;
	}

	public void setPlanElectiveCourses(List<PlanElectiveCourseTO> planElectiveCourseTOs) {
		this.planElectiveCourses = planElectiveCourseTOs;
	}

    public String getBasedOnTemplateId() {
        return basedOnTemplateId;
    }

    public void setBasedOnTemplateId(String basedOnTemplateId) {
        this.basedOnTemplateId = basedOnTemplateId;
    }
}