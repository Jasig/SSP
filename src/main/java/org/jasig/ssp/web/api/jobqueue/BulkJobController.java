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
package org.jasig.ssp.web.api.jobqueue;

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonEmailService;
import org.jasig.ssp.transferobject.form.BulkEmailStudentRequestForm;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/1/bulk")
public class BulkJobController  extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BulkJobController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Autowired
	private PersonEmailService personEmailService;

	@PreAuthorize("hasRole('ROLE_PERSON_WRITE') and hasRole('ROLE_BULK_EMAIL_STUDENT')")
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public @ResponseBody
	JobTO bulkEmail(@RequestBody BulkEmailStudentRequestForm form)
			throws ObjectNotFoundException, ValidationException, IOException {
		return personEmailService.emailStudentsInBulk(form);
	}

}
