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
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSelfHelpGuideResponseService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.SelfHelpGuideQuestionService;
import org.jasig.ssp.service.reference.SelfHelpGuideService;
import org.jasig.ssp.transferobject.SelfHelpGuideResponseTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;

public class MyGpsSelfHelpGuideResponseControllerTest {

	private transient MyGpsSelfHelpGuideResponseController controller;

	private transient PersonSelfHelpGuideResponseService service;
	private transient SelfHelpGuideService selfHelpGuideService;
	private transient SelfHelpGuideQuestionService selfHelpGuideQuestionService;
	private transient SecurityServiceInTestEnvironment securityService;

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(MyGpsSelfHelpGuideResponseControllerTest.class);

	@Before
	public void setUp() {
		service = createMock(PersonSelfHelpGuideResponseService.class);
		selfHelpGuideService = createMock(SelfHelpGuideService.class);
		selfHelpGuideQuestionService = createMock(SelfHelpGuideQuestionService.class);
		securityService = new SecurityServiceInTestEnvironment();

		controller = new MyGpsSelfHelpGuideResponseController(service,
				selfHelpGuideService, selfHelpGuideQuestionService,
				securityService);
	}

	@Test
	public void cancel() throws Exception {
		UUID selfHelpGuideResponseId = UUID.randomUUID();
		SelfHelpGuideResponse selfHelpGuideResponse = new SelfHelpGuideResponse();

		expect(service.get(selfHelpGuideResponseId)).andReturn(
				selfHelpGuideResponse);
		expect(service.cancelSelfHelpGuideResponse(selfHelpGuideResponse))
				.andReturn(false);

		replay(service);

		Boolean response = controller.cancel(selfHelpGuideResponseId);

		verify(service);
		assertFalse(response);
	}

	@Test
	public void complete() throws Exception {
		UUID selfHelpGuideResponseId = UUID.randomUUID();
		SelfHelpGuideResponse selfHelpGuideResponse = new SelfHelpGuideResponse();

		expect(service.get(selfHelpGuideResponseId)).andReturn(
				selfHelpGuideResponse);
		expect(service.completeSelfHelpGuideResponse(selfHelpGuideResponse))
				.andReturn(false);

		replay(service);

		Boolean response = controller.complete(selfHelpGuideResponseId);

		verify(service);
		assertFalse(response);
	}

	@Test
	public void getById() throws Exception {
		UUID selfHelpGuideResponseId = UUID.randomUUID();
		SelfHelpGuideResponse selfHelpGuideResponse = new SelfHelpGuideResponse();

		expect(service.get(selfHelpGuideResponseId)).andReturn(
				selfHelpGuideResponse);
		SelfHelpGuideResponseTO expectedResponseTO = new SelfHelpGuideResponseTO();
		expect(
				service.getSelfHelpGuideResponseFor(eq(selfHelpGuideResponse),
						isA(SortingAndPaging.class)))
				.andReturn(expectedResponseTO);

		replay(service);

		SelfHelpGuideResponseTO responseTO = controller
				.getById(selfHelpGuideResponseId);

		verify(service);
		assertEquals(responseTO, expectedResponseTO);
	}

	@Test
	public void initiate() throws Exception {
		Person bob = new Person(UUID.randomUUID());
		securityService.setCurrent(bob);

		UUID selfHelpGuideId = UUID.randomUUID();

		SelfHelpGuide selfHelpGuide = new SelfHelpGuide();
		SelfHelpGuideResponse selfHelpGuideResponse = new SelfHelpGuideResponse();

		expect(selfHelpGuideService.get(selfHelpGuideId)).andReturn(
				selfHelpGuide);

		expect(service.initiateSelfHelpGuideResponse(selfHelpGuide, bob))
				.andReturn(selfHelpGuideResponse);

		replay(service);
		replay(selfHelpGuideService);

		String response = controller.initiate(selfHelpGuideId);

		verify(service);
		verify(selfHelpGuideService);
		assertEquals(selfHelpGuideResponse.toString(), response);
	}

	@Test
	public void answer() throws ObjectNotFoundException {
		UUID selfHelpGuideResponseId = UUID.randomUUID();
		UUID selfHelpGuideQuestionId = UUID.randomUUID();
		Boolean answerResponse = true;
		SelfHelpGuideResponse selfHelpGuideResponse = new SelfHelpGuideResponse();
		SelfHelpGuideQuestion selfHelpGuideQuestion = new SelfHelpGuideQuestion();

		expect(service.get(selfHelpGuideResponseId)).andReturn(
				selfHelpGuideResponse);
		expect(selfHelpGuideQuestionService.get(selfHelpGuideQuestionId))
				.andReturn(selfHelpGuideQuestion);
		expect(
				service.answerSelfHelpGuideQuestion(
						selfHelpGuideResponse,
						selfHelpGuideQuestion, answerResponse))
				.andReturn(false);

		replay(service);
		replay(selfHelpGuideQuestionService);

		try {
			Boolean response = controller.answer(selfHelpGuideResponseId,
					selfHelpGuideQuestionId, answerResponse);

			verify(service);
			verify(selfHelpGuideQuestionService);
			assertFalse(response);
		} catch (Exception e) {
			fail("controller error");
		}
	}
}
