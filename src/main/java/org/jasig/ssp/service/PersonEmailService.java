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
import org.jasig.ssp.transferobject.form.BulkEmailStudentRequestForm;
import org.jasig.ssp.transferobject.form.EmailStudentRequestForm;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.InitializingBean;

import javax.mail.SendFailedException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * For handling requests to send email to {@link Person}s.
 */
public interface PersonEmailService extends InitializingBean {

	/**
	 * Send an email message to a single student, returning a MAP of descriptors enumerating the entities created
	 * as a result.
	 *
	 *
	 *
	 * @param emailRequest the email request
	 * @return a MAP of descriptors enumerating the entities created
	 * @throws ObjectNotFoundException if data was not present
	 * @throws ValidationException if data is invalid
	 */
	Map<String, UUID> emailStudent(EmailStudentRequestForm emailRequest) throws ObjectNotFoundException, ValidationException;

	/**
	 * Send an email message to potentially n-many students. This operation is expected to be implemented asynchronously
	 * so the return is a pointer to a work queue job.
	 *
	 * @param emailRequest the email request
	 * @return the transfer object
	 * @throws ObjectNotFoundException if data was not present
	 * @throws IOException if file io issue
	 * @throws ValidationException if data is invalid
	 * @throws SecurityException if security issue
	 */
	JobTO emailStudentsInBulk(BulkEmailStudentRequestForm emailRequest) throws ObjectNotFoundException, IOException,
			ValidationException, SecurityException;

	void sendCoachingAssignmentChangeEmail(Person model, UUID oldCoachId)
			throws ObjectNotFoundException, SendFailedException,
			ValidationException;
}
