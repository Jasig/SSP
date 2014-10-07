/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.jobqueue.JobExecutionResult;
import org.jasig.ssp.service.jobqueue.JobExecutionStatus;
import org.jasig.ssp.service.jobqueue.impl.AbstractJobExecutor;
import org.jasig.ssp.transferobject.ImmutablePersonIdentifiersTO;
import org.jasig.ssp.transferobject.form.BulkEmailJobSpec;
import org.jasig.ssp.transferobject.form.BulkEmailStudentRequestForm;
import org.jasig.ssp.transferobject.form.EmailAddress;
import org.jasig.ssp.transferobject.form.EmailStudentRequestForm;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.mail.SendFailedException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
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
	 * @param emailRequest
	 * @return
	 * @throws ObjectNotFoundException
	 * @throws ValidationException
	 */
	Map<String,UUID> emailStudent(EmailStudentRequestForm emailRequest) throws ObjectNotFoundException, ValidationException;

	/**
	 * Send an email message to potentially n-many students. This operation is expected to be implemented asynchronously
	 * so the return is a pointer to a work queue job.
	 *
	 * @param emailRequest
	 * @return
	 */
	JobTO emailStudentsInBulk(BulkEmailStudentRequestForm emailRequest) throws ObjectNotFoundException, IOException,
			ValidationException, SecurityException;

	void sendCoachingAssignmentChangeEmail(Person model, UUID oldCoachId)
			throws ObjectNotFoundException, SendFailedException,
			ValidationException;
}
