package org.jasig.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.UUID;

import org.jasig.mygps.business.SelfHelpGuideManager;
import org.jasig.mygps.model.transferobject.SelfHelpGuideResponseTO;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyGpsSelfHelpGuideResponseControllerTest {

	private transient MyGpsSelfHelpGuideResponseController controller;

	private transient SelfHelpGuideManager manager;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsSelfHelpGuideResponseControllerTest.class);

	@Before
	public void setup() {
		manager = createMock(SelfHelpGuideManager.class);

		controller = new MyGpsSelfHelpGuideResponseController();
		controller.setManager(manager);
	}

	@Test
	public void cancel() throws ObjectNotFoundException {
		UUID selfHelpGuideResponseId = UUID.randomUUID();
		expect(manager.cancelSelfHelpGuideResponse(selfHelpGuideResponseId))
				.andReturn(false);

		replay(manager);

		try {
			Boolean response = controller.cancel(selfHelpGuideResponseId);

			verify(manager);
			assertFalse(response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void complete() throws ObjectNotFoundException {
		UUID selfHelpGuideResponseId = UUID.randomUUID();
		expect(manager.completeSelfHelpGuideResponse(selfHelpGuideResponseId))
				.andReturn(false);

		replay(manager);

		try {
			Boolean response = controller.complete(selfHelpGuideResponseId);

			verify(manager);
			assertFalse(response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void getById() throws ObjectNotFoundException {
		UUID selfHelpGuideResponseId = UUID.randomUUID();
		SelfHelpGuideResponseTO expectedResponseTO = new SelfHelpGuideResponseTO();
		expect(manager.getSelfHelpGuideResponseById(selfHelpGuideResponseId))
				.andReturn(expectedResponseTO);

		replay(manager);

		try {
			SelfHelpGuideResponseTO responseTO = controller
					.getById(selfHelpGuideResponseId);

			verify(manager);
			assertEquals(responseTO, expectedResponseTO);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void initiate() {
		UUID expectedResponse = UUID.randomUUID();
		UUID selfHelpGuideResponseId = UUID.randomUUID();
		expect(manager.initiateSelfHelpGuideResponse(selfHelpGuideResponseId))
				.andReturn(expectedResponse);

		replay(manager);

		try {
			String response = controller.initiate(selfHelpGuideResponseId);

			verify(manager);
			assertEquals(expectedResponse.toString(), response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void answer() throws ObjectNotFoundException {
		UUID selfHelpGuideResponseId = UUID.randomUUID();
		UUID selfHelpGuideQuestionId = UUID.randomUUID();
		Boolean answerResponse = true;

		expect(
				manager.answerSelfHelpGuideQuestion(selfHelpGuideResponseId,
						selfHelpGuideQuestionId, answerResponse)).andReturn(
				false);

		replay(manager);

		try {
			Boolean response = controller.answer(selfHelpGuideResponseId,
					selfHelpGuideQuestionId, answerResponse);

			verify(manager);
			assertFalse(response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}
}
