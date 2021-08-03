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
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.transferobject.form.BulkProgramStatusChangeRequestForm;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.UUID;

/**
 * PersonProgramStatus service
 * 
 * @author jon.adams
 * 
 */
public interface PersonProgramStatusService
		extends PersonAssocAuditableService<PersonProgramStatus> {

	/**
	 * Gets the only program status that is not marked expired, if there is one.
	 * 
	 * @param personId
	 *            person identifier
	 * @return the only program status that is not marked expired, if there is
	 *         one
	 * @throws ObjectNotFoundException
	 *             If personId was not found.
	 * @throws ValidationException
	 *             If data is not valid.
	 */
	PersonProgramStatus getCurrent(@NotNull UUID personId)
			throws ObjectNotFoundException, ValidationException;

    /**
     * Set the program status for the specified student to Active
     *
     * @param person the person object
     * @throws ObjectNotFoundException If data is not found
     * @throws ValidationException If data is not valid.
     */
    void setActiveForStudent(@NotNull Person person) throws ObjectNotFoundException, ValidationException;

	/**
	 * Sets the program status for the specified student to Transitioned.
	 * 
	 * @param person
	 *            the person
	 * @throws ObjectNotFoundException
	 *             If person could not be found
	 * @throws ValidationException
	 *             Shouldn't be thrown, unless a bug setting the
	 *             {@link PersonProgramStatus} exists in the code.
	 */
	void setTransitionForStudent(@NotNull Person person)
			throws ObjectNotFoundException, ValidationException;

	/**
	 * Expires any active Program Statuses for a student
	 * @param person the Person object
	 * @param savingStatus the PersonProgramStatus object
	 * @throws ValidationException if data is not valid
	 */
	void expireActive(Person person, PersonProgramStatus savingStatus)
			throws ValidationException;

    /**
     * Bulk Changes Program Status for Several Students
     * @param form the bulk program status change request form
     * @return the job
     * @throws IOException if file access exception
     * @throws ObjectNotFoundException if data not found
     * @throws ValidationException if data invalid
     * @throws SecurityException if security issue
     */
	JobTO changeInBulk(BulkProgramStatusChangeRequestForm form) throws IOException, ObjectNotFoundException,
			ValidationException, SecurityException;
}