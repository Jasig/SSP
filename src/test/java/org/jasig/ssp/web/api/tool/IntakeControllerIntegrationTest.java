package org.jasig.ssp.web.api.tool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.transferobject.reference.ChallengeReferralTO;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.transferobject.tool.IntakeFormTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link IntakeController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class IntakeControllerIntegrationTest {

	@Autowired
	private transient IntakeController controller;

	private static final UUID STUDENT_ID = UUID
			.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194");

	private static final String STUDENT_FIRSTNAME = "Dennis";

	/**
	 * Test the {@link IntakeController#load(UUID)} action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerLoad() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final IntakeFormTO obj = controller.load(STUDENT_ID);

		assertNotNull(
				"Returned IntakeFormTO from the controller should not have been null.",
				obj);

		assertEquals("Returned Person.FirstName did not match.",
				STUDENT_FIRSTNAME, obj.getPerson().getFirstName());
	}

	/**
	 * Test the {@link IntakeController#referenceData()} action.
	 * 
	 * This test assumes that there is at least 1 valid, active
	 * ChallengeReferral in the test database.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerRefData() throws Exception {
		final Map<String, Object> data = controller.referenceData();

		assertNotNull("The map should not have been null.", data);

		@SuppressWarnings("unchecked")
		final List<ChallengeTO> challenges = (List<ChallengeTO>) data
				.get("challenges");
		assertNotNull("The Challenges should not have been null.", challenges);

		for (ChallengeTO challenge : challenges) {
			for (ChallengeReferralTO referral : challenge
					.getChallengeChallengeReferrals()) {
				assertTrue(
						"All Referrals with !ShowInStudentIntake should not have been returned.",
						referral.isShowInStudentIntake());
			}
		}
	}
}
