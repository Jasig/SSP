package org.studentsuccessplan.mygps.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.mygps.model.transferobject.SelfHelpGuideContentTO;
import org.studentsuccessplan.mygps.model.transferobject.SelfHelpGuideResponseTO;
import org.studentsuccessplan.ssp.dao.SelfHelpGuideQuestionResponseDao;
import org.studentsuccessplan.ssp.dao.SelfHelpGuideResponseDao;
import org.studentsuccessplan.ssp.dao.reference.ChallengeDao;
import org.studentsuccessplan.ssp.dao.reference.SelfHelpGuideDao;
import org.studentsuccessplan.ssp.dao.reference.SelfHelpGuideQuestionDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.SelfHelpGuideQuestionResponse;
import org.studentsuccessplan.ssp.model.SelfHelpGuideResponse;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.service.reference.ChallengeReferralService;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

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

	/**
	 * Retrieves the specified guide with associated questions.
	 * 
	 * @param selfHelpGuideId
	 *            The guide to load
	 * @return The specified guide with associated questions.
	 * @throws ObjectNotFoundException
	 *             If the specified guide could not be found.
	 */
	public SelfHelpGuideContentTO getContentById(UUID selfHelpGuideId)
			throws ObjectNotFoundException {

		// Look up specified guide
		SelfHelpGuide selfHelpGuide = selfHelpGuideDao.get(selfHelpGuideId);

		if (selfHelpGuide == null) {
			throw new ObjectNotFoundException(
					"Specified guide could not be loaded.");
		}

		// Create, fill, and return the SelfHelpGuideContentTO response
		SelfHelpGuideContentTO selfHelpGuideContentTO = new SelfHelpGuideContentTO();
		selfHelpGuideContentTO.fromModel(selfHelpGuide,
				selfHelpGuideQuestionDao.bySelfHelpGuide(selfHelpGuideId));

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

		final SortingAndPaging sAndP = new SortingAndPaging(ObjectStatus.ACTIVE);
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
					.getChallengeReferralCountByChallengeAndQuery(challenge,
							"", sAndP);

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