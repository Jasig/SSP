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
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.PlanCourse;
import org.jasig.ssp.model.PlanElectiveCourse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class PlanCourseTO extends AbstractPlanCourseTO<Plan,PlanCourse> {

	public static class PlanCourseIndexComparator implements Comparator<PlanCourseTO> {
		 @Override
		    public int compare(PlanCourseTO o1, PlanCourseTO o2) {
		        return o1.getOrderInTerm().compareTo(o2.getOrderInTerm());
		    }

	}

	public static final PlanCourseIndexComparator TERM_ORDER_COMPARATOR =
			new PlanCourseIndexComparator();
	
	private String personId;

	private UUID planElectiveCourseId;
	private List<AbstractMapElectiveCourseTO> planElectiveCourseElectives;

	public PlanCourseTO(PlanCourse planCourse) {
		super();
		from(planCourse);
	}

	/**
	 * Empty constructor.
	 */
	public PlanCourseTO() {
		super();
	}

	@Override
	public void from(PlanCourse model) {
		super.from(model);
		this.setPersonId(model.getPerson().getId().toString());
		this.setIsTranscript(model.getIsTranscript());
		this.setDuplicateOfTranscript(model.getDuplicateOfTranscript());

		PlanElectiveCourse planElectiveCourse = model.getPlanElectiveCourse();
		if (planElectiveCourse!=null) {
			planElectiveCourseId = planElectiveCourse.getId();
			planElectiveCourseElectives = new ArrayList<>();
			planElectiveCourseElectives.add(new PlanElectiveCourseTO(planElectiveCourse));
			for (AbstractMapElectiveCourse electiveCourseElective : planElectiveCourse.getElectiveCourseElectives()) {
				planElectiveCourseElectives.add(new PlanElectiveCourseTO(electiveCourseElective));
			}
		}
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
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
