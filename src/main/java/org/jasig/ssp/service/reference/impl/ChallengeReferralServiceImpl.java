package org.jasig.ssp.service.reference.impl;

import java.util.List;

import org.jasig.ssp.dao.reference.ChallengeReferralDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChallengeReferralServiceImpl extends
		AbstractReferenceService<ChallengeReferral>
		implements ChallengeReferralService {

	@Autowired
	transient private ChallengeReferralDao dao;

	@Autowired
	private transient TaskService taskService;

	@Autowired
	private transient SecurityService securityService;

	public ChallengeReferralServiceImpl() {
		super();
	}

	@Override
	public List<ChallengeReferral> getChallengeReferralsByChallengeId(
			final Challenge challenge) {
		return dao.byChallengeId(challenge.getId());
	}

	@Override
	public List<ChallengeReferral> challengeReferralSearch(
			final Challenge challenge) {
		return dao.byChallengeIdNotOnActiveTaskList(challenge.getId(),
				securityService.currentUser().getPerson(),
				securityService.getSessionId());
	}

	@Override
	public int getChallengeReferralCountByChallengeAndQuery(
			final Challenge challenge, final String query,
			final SortingAndPaging sAndP) {

		int count = 0;

		for (ChallengeReferral challengeReferral : dao.byChallengeIdAndQuery(
				challenge.getId(), query)) {

			// Does the referral exist as an active/incomplete task?
			// Need to check both the tasks created w/in MyGPS as well as those
			// created in SSP.

			boolean isEmpty = false;

			if (securityService.isAuthenticated()) {
				final Person student = securityService.currentUser()
						.getPerson();
				isEmpty = taskService.getAllForPersonAndChallengeReferral(
						student,
						false, challengeReferral, sAndP).isEmpty();
			} else {
				isEmpty = taskService.getAllForSessionIdAndChallengeReferral(
						securityService.getSessionId(), false,
						challengeReferral, sAndP).isEmpty();
			}

			if (isEmpty) {
				count++;
			}
		}

		return count;
	}

	@Override
	public long countByChallengeIdNotOnActiveTaskList(
			final Challenge challenge,
			final Person student, final String sessionId) {
		return dao.countByChallengeIdNotOnActiveTaskList(challenge.getId(),
				student, sessionId);
	}

	@Override
	public List<ChallengeReferral> byChallengeIdNotOnActiveTaskList(
			Challenge challenge, Person student, String sessionId) {
		return dao.byChallengeIdNotOnActiveTaskList(challenge.getId(), student,
				sessionId);
	}

	@Override
	protected ChallengeReferralDao getDao() {
		return dao;
	}

	protected void setDao(ChallengeReferralDao dao) {
		this.dao = dao;
	}
}
