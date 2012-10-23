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
package org.jasig.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertTrue;

import javax.mail.SendFailedException;

import org.jasig.mygps.model.transferobject.MessageTO;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

public class MyGpsMessageControllerTest {

	private transient MyGpsMessageController controller;

	private transient MessageService service;

	private transient MessageTemplateService messageTemplateService;

	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		service = createMock(MessageService.class);
		messageTemplateService = createMock(MessageTemplateService.class);
		securityService = new SecurityServiceInTestEnvironment();
		controller = new MyGpsMessageController(service,
				messageTemplateService, securityService);
	}

	@Test
	public void contactCoach() throws SendFailedException,
			ObjectNotFoundException, ValidationException {
		final Person current = new Person();
		current.setFirstName("Joe");
		current.setLastName("Student");
		securityService.setCurrent(current);

		final Person coach = new Person();
		current.setCoach(coach);

		final SubjectAndBody subjAndBody = new SubjectAndBody("Test Subject",
				"Test Body");

		final MessageTO tObject = new MessageTO();
		tObject.setMessage(subjAndBody.getBody());
		tObject.setSubject(subjAndBody.getSubject());

		expect(
				service.createMessage(eq(coach), (String) isNull(),
						eq(subjAndBody)))
				.andReturn(null);
		expect(
				messageTemplateService.createContactCoachMessage(
						subjAndBody.getBody(), subjAndBody.getSubject(),
						current)).andReturn(subjAndBody);

		replay(service);
		replay(messageTemplateService);

		assertTrue("Controller should pass back true if it succeeds",
				controller.contactCoach(tObject));

		verify(service);
		verify(messageTemplateService);
	}
}
