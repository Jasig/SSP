package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ChallengeReferralDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.service.TaskService;
import org.studentsuccessplan.ssp.service.reference.ChallengeReferralService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
public class ChallengeReferralServiceImpl implements ChallengeReferralService {

	@Autowired
	private ChallengeReferralDao dao;

	@Autowired
	private TaskService taskService;

	@Autowired
	private SecurityService securityService;

	@Override
	public List<ChallengeReferral> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public ChallengeReferral get(UUID id) throws ObjectNotFoundException {
		ChallengeReferral obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "ChallengeReferral");
		}

		return obj;
	}

	@Override
	public ChallengeReferral create(ChallengeReferral obj) {
		return dao.save(obj);
	}

	@Override
	public ChallengeReferral save(ChallengeReferral obj)
			throws ObjectNotFoundException {
		ChallengeReferral current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		ChallengeReferral current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	@Override
	public List<ChallengeReferral> getChallengeReferralsByChallengeId(
			Challenge challenge) {
		return dao.byChallengeId(challenge.getId());
	}

	@Override
	public List<ChallengeReferral> challengeReferralSearch(Challenge challenge) {
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

			int size = 0;

			if (securityService.isAuthenticated()) {
				Person student = securityService.currentUser().getPerson();
				size = taskService.getAllForPersonAndChallengeReferral(student,
						false, challengeReferral, sAndP).size();
			} else {
				size = taskService.getAllForSessionIdAndChallengeReferral(
						securityService.getSessionId(), false,
						challengeReferral, sAndP).size();
			}

			if (size == 0) {
				count++;
			}
		}

		return count;
	}

	@Override
	public int countByChallengeIdNotOnActiveTaskList(Challenge challenge,
			Person student, String sessionId) {
		return dao.countByChallengeIdNotOnActiveTaskList(challenge.getId(),
				student, sessionId);
	}

	@Override
	public List<ChallengeReferral> byChallengeIdNotOnActiveTaskList(
			Challenge challenge, Person student, String sessionId) {
		return dao.byChallengeIdNotOnActiveTaskList(challenge.getId(), student,
				sessionId);
	}

	protected void setDao(ChallengeReferralDao dao) {
		this.dao = dao;
	}
}
