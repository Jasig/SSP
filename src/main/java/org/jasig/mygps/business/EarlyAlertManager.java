package org.jasig.mygps.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasig.ssp.dao.SelfHelpGuideQuestionResponseDao;
import org.jasig.ssp.dao.SelfHelpGuideResponseDao;
import org.jasig.ssp.model.SelfHelpGuideQuestionResponse;
import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class EarlyAlertManager {

	// EarlyAlertReferralReasonLU
	public static final String EARLY_ALERT_REFERRAL_REASON_SELF_HELP_GUIDE_THRESHOLD_EXCEEDED = "300D68EF-38C2-4B7D-AD46-7874AA5D34AC";
	public static final String EARLY_ALERT_REFERRAL_REASON_SELF_HELP_GUIDE_CRITICAL_QUESTION = "1F5729AF-0337-4E58-A001-8A9F80DBF8AA";

	// EarlyAlertFacultySuggestionLU
	public static final String EARLY_ALERT_FACULTY_SUGGESTION_SEE_ADVISOR_OR_COACH = "B2D11151-5056-A51A-80513ACDF99FEF84";

	// Early Alert Default Campus ID
	public static final String EARLY_ALERT_DEFAULT_CAMPUS_ID = "1";

	@Autowired
	private transient SelfHelpGuideQuestionResponseDao selfHelpGuideQuestionResponseDao;

	@Autowired
	private transient SelfHelpGuideResponseDao selfHelpGuideResponseDao;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertManager.class);

	@Transactional(readOnly = false)
	public void generateCriticalAlerts() {

		LOGGER.info("BEGIN : generateCriticalAlerts()");

		final List<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses = selfHelpGuideQuestionResponseDao
				.criticalResponsesForEarlyAlert();

		for (final SelfHelpGuideQuestionResponse selfHelpGuideQuestionResponse : selfHelpGuideQuestionResponses) {

			final RestTemplate restTemplate = new RestTemplate(); // NOPMD
			final Map<String, Object> params = new HashMap<String, Object>(); // NOPMD

			params.put("studentId", selfHelpGuideQuestionResponse
					.getSelfHelpGuideResponse().getPerson().getUsername());
			// TODO Default campuId needs pulled from database
			params.put("campusId", EARLY_ALERT_DEFAULT_CAMPUS_ID);
			params.put(
					"referralReason",
					EARLY_ALERT_REFERRAL_REASON_SELF_HELP_GUIDE_CRITICAL_QUESTION);
			params.put(
					"facultySuggestions",
					new String[] { EARLY_ALERT_FACULTY_SUGGESTION_SEE_ADVISOR_OR_COACH }); // NOPMD
			params.put("comment",
					"The following critical question was answered affirmatively: "
							+ selfHelpGuideQuestionResponse
									.getSelfHelpGuideQuestion().getChallenge()
									.getSelfHelpGuideQuestion());

			try {
				LOGGER.info("Sending Alert for student ID \""
						+ selfHelpGuideQuestionResponse
								.getSelfHelpGuideResponse().getPerson()
								.getUsername()
						+ "\" : generateCriticalAlerts()");

				// :TODO post to early alert api
				final String result = restTemplate.postForObject(
						"Something goes here"
								+ "/createEarlyAlert", params, String.class);

				if (Boolean.parseBoolean(result.trim())) {
					selfHelpGuideQuestionResponse.setEarlyAlertSent(true);
					selfHelpGuideQuestionResponseDao
							.save(selfHelpGuideQuestionResponse);

					LOGGER.info("Alert for selfHelpGuideQuestionResponse "
							+ selfHelpGuideQuestionResponse.getId()
							+ " sent successfully.");
				} else {
					LOGGER.error("ERROR : generateCriticalAlerts() : {}",
							"Return value false from post for student self help guide question response "
									+ selfHelpGuideQuestionResponse.getId());
				}

			} catch (final Exception e) {
				LOGGER.error("ERROR : generateCriticalAlerts() : {}",
						e.getMessage(), e);
			}

		}

		LOGGER.info("END : generateCriticalAlerts()");
	}

	public void generateThresholdAlerts() {

		LOGGER.info("BEGIN : generateThresholdAlerts()");

		final List<SelfHelpGuideResponse> selfHelpGuideResponses = selfHelpGuideResponseDao
				.forEarlyAlert();

		for (final SelfHelpGuideResponse selfHelpGuideResponse : selfHelpGuideResponses) {

			final RestTemplate restTemplate = new RestTemplate(); // NOPMD
			final Map<String, Object> params = new HashMap<String, Object>(); // NOPMD

			params.put("studentId", selfHelpGuideResponse.getPerson()
					.getUsername());
			params.put("campusId", EARLY_ALERT_DEFAULT_CAMPUS_ID);
			params.put(
					"referralReason",
					EARLY_ALERT_REFERRAL_REASON_SELF_HELP_GUIDE_THRESHOLD_EXCEEDED);
			params.put(
					"facultySuggestions",
					new String[] { EARLY_ALERT_FACULTY_SUGGESTION_SEE_ADVISOR_OR_COACH }); // NOPMD
			params.put("comment", "The threshold for the self help guide "
					+ selfHelpGuideResponse.getSelfHelpGuide().getName()
					+ " was exceeded.");

			try {
				LOGGER.info("Sending Alert for student "
						+ selfHelpGuideResponse.getPerson().getUsername()
						+ " : generateThresholdAlerts()");

				// :TODO post early alert
				final String result = restTemplate.postForObject(
						"something goes here"
								+ "/createEarlyAlert", params, String.class);

				if (Boolean.parseBoolean(result.trim())) {
					selfHelpGuideResponse.setEarlyAlertSent(true);
					selfHelpGuideResponseDao.save(selfHelpGuideResponse);

					LOGGER.info("Alert for selfhelpguideresponse "
							+ selfHelpGuideResponse.getId()
							+ " sent successfully.");
				} else {
					LOGGER.error("ERROR : generateThresholdAlerts() : {}",
							"Return value false from post for student self help guide response "
									+ selfHelpGuideResponse.getId());
				}

			} catch (final Exception e) {
				LOGGER.error("ERROR : generateThresholdAlerts() : {}",
						e.getMessage(), e);
			}

		}

		LOGGER.info("END : generateThresholdAlerts()");
	}
}
