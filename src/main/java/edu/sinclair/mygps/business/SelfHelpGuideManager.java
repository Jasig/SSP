package edu.sinclair.mygps.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.mygps.dao.ActionPlanStepDao;
import edu.sinclair.mygps.dao.SelfHelpGuideQuestionResponseDao;
import edu.sinclair.mygps.dao.SelfHelpGuideResponseDao;
import edu.sinclair.mygps.factory.SelfHelpGuideFactory;
import edu.sinclair.mygps.model.transferobject.ChallengeReferralTO;
import edu.sinclair.mygps.model.transferobject.SelfHelpGuideContentTO;
import edu.sinclair.mygps.model.transferobject.SelfHelpGuideQuestionTO;
import edu.sinclair.mygps.model.transferobject.SelfHelpGuideResponseTO;
import edu.sinclair.mygps.model.transferobject.SelfHelpGuideTO;
import edu.sinclair.ssp.dao.TaskDao;
import edu.sinclair.ssp.dao.reference.ChallengeDao;
import edu.sinclair.ssp.dao.reference.ChallengeReferralDao;
import edu.sinclair.ssp.dao.reference.SelfHelpGuideDao;
import edu.sinclair.ssp.dao.reference.SelfHelpGuideQuestionDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.SelfHelpGuideQuestionResponse;
import edu.sinclair.ssp.model.SelfHelpGuideResponse;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.model.reference.ChallengeReferral;
import edu.sinclair.ssp.model.reference.SelfHelpGuide;
import edu.sinclair.ssp.model.reference.SelfHelpGuideQuestion;
import edu.sinclair.ssp.service.SecurityService;
import edu.sinclair.ssp.transferobject.reference.ChallengeTO;

@Service
public class SelfHelpGuideManager {

	@Autowired
	private ActionPlanStepDao actionPlanStepDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private ChallengeDao challengeDao;

	@Autowired
	private ChallengeReferralDao challengeReferralDao;

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

	public List<SelfHelpGuideTO> getAll() {

		List<SelfHelpGuideTO> selfHelpGuideTOs = null;

		if (!securityService.isAuthenticated()) {
			selfHelpGuideTOs = SelfHelpGuideFactory
					.selfHelpGuidesToSelfHelpGuideTOs(selfHelpGuideDao
							.findAllActiveForUnauthenticated());
		} else {
			selfHelpGuideTOs = SelfHelpGuideFactory
					.selfHelpGuidesToSelfHelpGuideTOs(selfHelpGuideDao
							.getAll(ObjectStatus.ACTIVE));
		}

		return selfHelpGuideTOs;
	}

	public List<SelfHelpGuideTO> getBySelfHelpGuideGroup(
			UUID selfHelpGuideGroupId) {
		return SelfHelpGuideFactory
				.selfHelpGuidesToSelfHelpGuideTOs(selfHelpGuideDao
						.selectActiveBySelfHelpGuideGroup(selfHelpGuideGroupId));
	}

	public SelfHelpGuideContentTO getContentById(UUID selfHelpGuideId) {

		SelfHelpGuideContentTO selfHelpGuideContentTO = new SelfHelpGuideContentTO();
		List<SelfHelpGuideQuestionTO> selfHelpGuideQuestionTOs = new ArrayList<SelfHelpGuideQuestionTO>();

		SelfHelpGuide selfHelpGuide = selfHelpGuideDao.get(selfHelpGuideId);

		for (SelfHelpGuideQuestion selfHelpGuideQuestion : selfHelpGuideQuestionDao
				.selectBySelfHelpGuide(selfHelpGuide.getId())) {
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
				.selectById(selfHelpGuideResponseId);

		selfHelpGuideResponse.setCancelled(true);

		selfHelpGuideResponseDao.save(selfHelpGuideResponse);

		return true;
	}

	// SelfHelpGuideResponseService
	public Boolean completeSelfHelpGuideResponse(UUID selfHelpGuideResponseId) {
		SelfHelpGuideResponse selfHelpGuideResponse = selfHelpGuideResponseDao
				.selectById(selfHelpGuideResponseId);
		selfHelpGuideResponse.setCompleted(true);
		selfHelpGuideResponseDao.save(selfHelpGuideResponse);
		return true;
	}

	public SelfHelpGuideResponseTO getSelfHelpGuideResponseById(
			UUID selfHelpGuideResponseId) {

		SelfHelpGuideResponse selfHelpGuideResponse = selfHelpGuideResponseDao
				.selectById(selfHelpGuideResponseId);
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

			count = getChallengeReferralCountByChallengeAndQuery(
					challenge.getId(), "");

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
		selfHelpGuideResponse.setPerson(securityService
				.currentlyLoggedInSspUser().getPerson());
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
						.selectById(selfHelpGuideResponseId));

		selfHelpGuideQuestionResponseDao.save(selfHelpGuideQuestionResponse);

		return true;
	}

	public List<ChallengeTO> challengeSearch(String query) {
		List<ChallengeTO> challengeTOs = new ArrayList<ChallengeTO>();

		Set<Challenge> challenges = new LinkedHashSet<Challenge>();

		challenges.addAll(challengeDao.searchByQuery(query));

		for (Challenge challenge : challenges) {
			long count = 0;

			count = challengeReferralDao
					.selectCountByChallengeNotOnActiveTaskList(challenge
							.getId(), securityService
							.currentlyLoggedInSspUser().getPerson(),
							securityService.getSessionId());

			if (count > 0) {
				ChallengeTO challengeTO = new ChallengeTO();

				challengeTO.setDescription(challenge.getDescription());
				challengeTO.setId(challenge.getId());
				challengeTO.setName(challenge.getName());

				challengeTOs.add(challengeTO);
			}
		}

		return challengeTOs;
	}

	public List<ChallengeReferralTO> challengeReferralSearch(UUID challengeId) {
		List<ChallengeReferralTO> challengeReferralTOs = new ArrayList<ChallengeReferralTO>();

		for (ChallengeReferral challengeReferral : challengeReferralDao
				.selectByChallengeNotOnActiveTaskList(challengeId,
						securityService.currentlyLoggedInSspUser().getPerson(),
						securityService.getSessionId())) {
			challengeReferralTOs
					.add(new ChallengeReferralTO(challengeReferral));
		}

		return challengeReferralTOs;
	}

	public List<ChallengeReferralTO> getChallengeReferralsByChallengeId(
			UUID challengeId) {

		List<ChallengeReferralTO> challengeReferralTOs = new ArrayList<ChallengeReferralTO>();

		for (ChallengeReferral challengeReferral : challengeReferralDao
				.selectByChallenge(challengeId)) {
			challengeReferralTOs
					.add(new ChallengeReferralTO(challengeReferral));
		}

		return challengeReferralTOs;
	}

	private int getChallengeReferralCountByChallengeAndQuery(UUID challengeId,
			String query) {

		int count = 0;

		for (ChallengeReferral challengeReferral : challengeReferralDao
				.searchByChallengeAndQuery(challengeId, query)) {

			// Does the referral exist as an active/incomplete task?
			// Need to check both the tasks created w/in MyGPS as well as those
			// created in SSP.

			int size = 0;

			if (securityService.isAuthenticated()) {
				Person student = securityService.currentlyLoggedInSspUser()
						.getPerson();
				size = taskDao.getAllForPersonIdAndChallengeReferralId(
						student.getId(), false, challengeReferral.getId())
						.size()
						+ actionPlanStepDao
								.selectAllIncompleteByPersonAndChallengeReferral(
										student.getId(),
										challengeReferral.getId()).size();
			} else {
				size = taskDao.getAllForSessionIdAndChallengeReferralId(
						securityService.getSessionId(), false,
						challengeReferral.getId()).size();
			}

			if (size == 0) {
				count++;
			}
		}

		return count;
	}

	public void setSelfHelpGuideDao(SelfHelpGuideDao selfHelpGuideDao) {
		this.selfHelpGuideDao = selfHelpGuideDao;
	}

	public void setSelfHelpGuideResponseDao(
			SelfHelpGuideResponseDao selfHelpGuideResponseDao) {
		this.selfHelpGuideResponseDao = selfHelpGuideResponseDao;
	}

	public void setSelfHelpGuideQuestionResponseDao(
			SelfHelpGuideQuestionResponseDao selfHelpGuideQuestionResponseDao) {
		this.selfHelpGuideQuestionResponseDao = selfHelpGuideQuestionResponseDao;
	}

}
