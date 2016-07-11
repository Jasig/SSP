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
package org.jasig.ssp.transferobject.messagetemplate;

import org.jasig.ssp.model.Person;

import java.util.List;

public class StudentSpecialServiceGroupCourseWithdrawalMessageTemplateTO extends StudentPersonLiteMessageTemplateTO {

	private static final long serialVersionUID = 3778851520157295145L;

	private List<CourseSpecialServiceGroupCourseWithdrawalMessageTemplateTO> courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs;

	public StudentSpecialServiceGroupCourseWithdrawalMessageTemplateTO() {
	}

	public StudentSpecialServiceGroupCourseWithdrawalMessageTemplateTO(Person person, List<CourseSpecialServiceGroupCourseWithdrawalMessageTemplateTO> courses) {
		super(person);
		this.setCourses(courses);
	}

	public List<CourseSpecialServiceGroupCourseWithdrawalMessageTemplateTO> getCourses() {
		return courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs;
	}

	public void setCourses(List<CourseSpecialServiceGroupCourseWithdrawalMessageTemplateTO> courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs) {
		this.courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs = courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs;
	}
}
