package org.jasig.mygps.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.mygps.model.transferobject.SelfHelpGuideResponseTO;
import org.jasig.ssp.dao.SelfHelpGuideQuestionResponseDao;
import org.jasig.ssp.dao.SelfHelpGuideResponseDao;
import org.jasig.ssp.dao.reference.ChallengeDao;
import org.jasig.ssp.dao.reference.SelfHelpGuideDao;
import org.jasig.ssp.dao.reference.SelfHelpGuideQuestionDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.SelfHelpGuideQuestionResponse;
import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideDetailTO;
import org.jasig.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
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
	public SelfHelpGuideDetailTO getContentById(UUID selfHelpGuideId)
			throws ObjectNotFoundException {

		// Look up specified guide
		SelfHelpGuide selfHelpGuide = selfHelpGuideDao.get(selfHelpGuideId);

		if (selfHelpGuide == null) {
			throw new ObjectNotFoundException(
					"Specified guide could not be loaded.");
		}

		selfHelpGuide.setSelfHelpGuideQuestions(selfHelpGuideQuestionDao
				.bySelfHelpGuide(selfHelpGuideId));

		// Create, fill, and return the SelfHelpGuideDetailTO response
		SelfHelpGuideDetailTO selfHelpGuideDetailTO = new SelfHelpGuideDetailTO();
		selfHelpGuideDetailTO.from(selfHelpGuide);

		return selfHelpGuideDetailTO;
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
		return selfHelpGuideResponseDao.save(selfHelpGuideResponse)
				.isCompleted();
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