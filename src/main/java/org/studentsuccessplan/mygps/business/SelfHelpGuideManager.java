package org.studentsuccessplan.mygps.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.mygps.model.transferobject.SelfHelpGuideContentTO;
import org.studentsuccessplan.mygps.model.transferobject.SelfHelpGuideQuestionTO;
import org.studentsuccessplan.mygps.model.transferobject.SelfHelpGuideResponseTO;
import org.studentsuccessplan.ssp.dao.SelfHelpGuideQuestionResponseDao;
import org.studentsuccessplan.ssp.dao.SelfHelpGuideResponseDao;
import org.studentsuccessplan.ssp.dao.reference.ChallengeDao;
import org.studentsuccessplan.ssp.dao.reference.SelfHelpGuideDao;
import org.studentsuccessplan.ssp.dao.reference.SelfHelpGuideQuestionDao;
import org.studentsuccessplan.ssp.model.SelfHelpGuideQuestionResponse;
import org.studentsuccessplan.ssp.model.SelfHelpGuideResponse;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.service.reference.ChallengeReferralService;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;

@Service
public class SelfHelpGuideManager {

	@Autowired
	private ChallengeDao challengeDao;

	@Autowired
	private ChallengeReferralService challengeReferralService;

	@Autowired
	private SelfHelpGuideDao selfHelpGuideDao;

	@Autowired
	private SelfHelpGuideResponseDao selfHelpGuideResponseDao;

	@Autowired
	private SelfHelpGuideQuestionDao selfHelpGuideQuestionDao;

	@Autowired
	private SelfHelpGuideQuestionResponseDao selfHelpGuideQuestionResponseDao;

	@Autowired
	private SecurityService securityService;

	public SelfHelpGuideContentTO getContentById(UUID selfHelpGuideId) {

		SelfHelpGuideContentTO selfHelpGuideContentTO = new SelfHelpGuideContentTO();
		List<SelfHelpGuideQuestionTO> selfHelpGuideQuestionTOs = new ArrayList<SelfHelpGuideQuestionTO>();

		SelfHelpGuide selfHelpGuide = selfHelpGuideDao.get(selfHelpGuideId);

		for (SelfHelpGuideQuestion selfHelpGuideQuestion : selfHelpGuideQuestionDao
				.bySelfHelpGuide(selfHelpGuide.getId())) {
			SelfHelpGuideQuestionTO selfHelpGuideQuestionTO = new SelfHelpGuideQuestionTO();

			selfHelpGuideQuestionTO.setDescriptionText(selfHelpGuideQuestion
					.getChallenge().getSelfHelpGuideDescription());
			selfHelpGuideQuestionTO.setHeadingText(selfHelpGuideQuestion
					.getChallenge().getName());
			selfHelpGuideQuestionTO.setId(selfHelpGuideQuestion.getId());
			selfHelpGuideQuestionTO.setMandatory(selfHelpGuideQuestion
					.isMandatory());
			selfHelpGuideQuestionTO.setQuestionText(selfHelpGuideQuestion
					.getChallenge().getSelfHelpGuideQuestion());

			selfHelpGuideQuestionTOs.add(selfHelpGuideQuestionTO);
		}

		selfHelpGuideContentTO.setId(selfHelpGuide.getId());
		selfHelpGuideContentTO.setName(selfHelpGuide.getName());
		selfHelpGuideContentTO.setDescription(selfHelpGuide.getDescription());
		selfHelpGuideContentTO.setQuestions(selfHelpGuideQuestionTOs);
		selfHelpGuideContentTO.setIntroductoryText(selfHelpGuide
				.getIntroductoryText());

		return selfHelpGuideContentTO;
	}

	public boolean cancelSelfHelpGuideResponse(UUID selfHelpGuideResponseId) {

		SelfHelpGuideResponse selfHelpGuideResponse = selfHelpGuideResponseDao
				.get(selfHelpGuideResponseId);

		selfHelpGuideResponse.setCancelled(true);

		selfHelpGuideResponseDao.save(selfHelpGuideResponse);

		return true;
	}

	// SelfHelpGuideResponseService
	public Boolean completeSelfHelpGuideResponse(UUID selfHelpGuideResponseId) {
		SelfHelpGuideResponse selfHelpGuideResponse = selfHelpGuideResponseDao
				.get(selfHelpGuideResponseId);
		selfHelpGuideResponse.setCompleted(true);
		selfHelpGuideResponseDao.save(selfHelpGuideResponse);
		return true;
	}

	public SelfHelpGuideResponseTO getSelfHelpGuideResponseById(
			UUID selfHelpGuideResponseId) {

		SelfHelpGuideResponse selfHelpGuideResponse = selfHelpGuideResponseDao
				.get(selfHelpGuideResponseId);
		SelfHelpGuideResponseTO selfHelpGuideResponseTO = new SelfHelpGuideResponseTO();
		int count = 0;

		selfHelpGuideResponseTO.setId(selfHelpGuideResponse.getId());
		selfHelpGuideResponseTO.setSummaryText(selfHelpGuideResponse
				.getSelfHelpGuide().getSummaryText());
		selfHelpGuideResponseTO.setTriggeredEarlyAlert(selfHelpGuideResponse
				.isEarlyAlertSent());

		// Get identified challenges
		List<ChallengeTO> challengeTOs = new ArrayList<ChallengeTO>();

		for (Challenge challenge : challengeDao
				.selectAffirmativeBySelfHelpGuideResponseId(selfHelpGuideResponseId)) {

			count = challengeReferralService
					.getChallengeReferralCountByChallengeAndQuery(challenge, "");

			if (count > 0) {

				ChallengeTO challengeTO = new ChallengeTO();

				challengeTO.setDescription(challenge
						.getSelfHelpGuideDescription());
				challengeTO.setId(challenge.getId());
				challengeTO.setName(challenge.getName());

				challengeTOs.add(challengeTO);
			}
		}

		selfHelpGuideResponseTO.setChallengesIdentified(challengeTOs);

		return selfHelpGuideResponseTO;
	}

	@Transactional(readOnly = false)
	public UUID initiateSelfHelpGuideResponse(UUID selfHelpGuideId) {

		SelfHelpGuideResponse selfHelpGuideResponse = new SelfHelpGuideResponse();

		selfHelpGuideResponse.setCancelled(false);
		selfHelpGuideResponse.setCompleted(false);
		selfHelpGuideResponse.setCreatedDate(new Date());
		selfHelpGuideResponse.setEarlyAlertSent(false);
		selfHelpGuideResponse.setPerson(securityService.currentUser()
				.getPerson());
		selfHelpGuideResponse.setSelfHelpGuide(new SelfHelpGuide(
				selfHelpGuideId));

		selfHelpGuideResponseDao.save(selfHelpGuideResponse);

		return selfHelpGuideResponse.getId();
	}

	public Boolean answerSelfHelpGuideQuestion(UUID selfHelpGuideResponseId,
			UUID selfHelpGuideQuestionId, Boolean response) {

		SelfHelpGuideQuestionResponse selfHelpGuideQuestionResponse = new SelfHelpGuideQuestionResponse();

		selfHelpGuideQuestionResponse.setCreatedDate(new Date());
		selfHelpGuideQuestionResponse.setEarlyAlertSent(false);
		selfHelpGuideQuestionResponse.setResponse(response);
		selfHelpGuideQuestionResponse
				.setSelfHelpGuideQuestion(selfHelpGuideQuestionDao
						.get(selfHelpGuideQuestionId));
		selfHelpGuideQuestionResponse
				.setSelfHelpGuideResponse(selfHelpGuideResponseDao
						.get(selfHelpGuideResponseId));

		selfHelpGuideQuestionResponseDao.save(selfHelpGuideQuestionResponse);

		return true;
	}
}