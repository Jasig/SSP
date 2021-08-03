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
package org.jasig.ssp.service;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.WatchStudent;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.transferobject.form.BulkWatchChangeRequestForm;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;


/**
 * WatchStudent service
 */
public interface WatchStudentService extends PersonAssocAuditableService<WatchStudent> {

	WatchStudent get(UUID watcherId, UUID studentId);

	PagingWrapper<PersonSearchResult2> watchListFor(
			ProgramStatus programStatus, Person person, SortingAndPaging sAndP);

	void exportWatchListFor(PrintWriter writer, ProgramStatus programStatus, Person person,
			SortingAndPaging buildSortAndPage) throws IOException;

	Long watchListCountFor(ProgramStatus programStatus, Person person,
			SortingAndPaging buildSortAndPage);

	JobTO changeInBulk(BulkWatchChangeRequestForm form) throws IOException, ObjectNotFoundException, ValidationException;

    /** Returns a list of watchers for a student
     * @param studentId the student UUID
     * @return list of watchers for a student
     */
	PagingWrapper<Person> getWatchersForStudent(UUID studentId);
}
