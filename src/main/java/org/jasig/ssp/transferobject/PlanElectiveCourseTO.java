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

import org.jasig.ssp.model.PlanElectiveCourse;
import org.jasig.ssp.model.PlanElectiveCourseElective;

import java.util.ArrayList;
import java.util.List;

public class PlanElectiveCourseTO extends AbstractMapElectiveCourseTO<PlanElectiveCourse> {

	private List<PlanElectiveCourseElectiveTO> planElectiveCourseElectives = new ArrayList<PlanElectiveCourseElectiveTO>();

	/**
	 * Empty constructor.
	 */
	public PlanElectiveCourseTO() {
		super();
	}

	public PlanElectiveCourseTO(PlanElectiveCourse model) {
		super();
		from(model);

		List<PlanElectiveCourseElective> planElectiveCourseElectives = model.getElectiveCourseElectives();
		for (PlanElectiveCourseElective planElectiveCourseElective : planElectiveCourseElectives) {
			PlanElectiveCourseElectiveTO planElectiveCourseElectiveTO = new PlanElectiveCourseElectiveTO(planElectiveCourseElective);
			this.getPlanElectiveCourseElectives().add(planElectiveCourseElectiveTO);
		}
	}

	public void from(PlanElectiveCourse model) {
		super.from(model);
	}

	public List<PlanElectiveCourseElectiveTO> getPlanElectiveCourseElectives() {
		return planElectiveCourseElectives;
	}

	public void setPlanElectiveCourseElectives(List<PlanElectiveCourseElectiveTO> planElectiveCourseElectives) {
		this.planElectiveCourseElectives = planElectiveCourseElectives;
	}
}
