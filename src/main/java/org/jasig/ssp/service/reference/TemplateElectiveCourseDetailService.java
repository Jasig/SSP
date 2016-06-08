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
package org.jasig.ssp.service.reference;

import org.jasig.ssp.model.TemplateElectiveCourse;
import org.jasig.ssp.model.TemplateElectiveCourseElective;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;

import java.util.UUID;

/**
 * TemplateElectiveCourse service
 */
public interface TemplateElectiveCourseDetailService extends
		AuditableCrudService<TemplateElectiveCourse> {

	PagingWrapper<TemplateElectiveCourseElective> getElectiveCourseAssociationsForElectiveCourse (
			final UUID electiveCourseId) throws ObjectNotFoundException;

	void deleteAssociatedElective(UUID id) throws ObjectNotFoundException;

	TemplateElectiveCourse createTemplateElectiveCourse(TemplateElectiveCourse templateElectiveCourse);

	TemplateElectiveCourseElective createTemplateElectiveCourseElective(TemplateElectiveCourseElective templateElectiveCourseElective);

	void delete(TemplateElectiveCourse templateElectiveCourse);

	TemplateElectiveCourse get(String formattedCourse);
}