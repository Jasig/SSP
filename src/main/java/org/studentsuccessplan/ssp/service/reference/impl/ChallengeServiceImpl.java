package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ChallengeDao;
import org.studentsuccessplan.ssp.dao.reference.ConfidentialityLevelDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.service.reference.ChallengeReferralService;
import org.studentsuccessplan.ssp.service.reference.ChallengeService;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Lists;

@Service
@Transactional
public class ChallengeServiceImpl implements ChallengeService {

	@Autowired
	private transient ChallengeDao dao;

	@Autowired
	private transient ConfidentialityLevelDao confidentialityLevelDao;

	@Autowired
	private transient ChallengeReferralService challengeReferralService;

	@Autowired
	private transient SecurityService securityService;

	@Override
	public PagingWrapper<Challenge> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public Challenge get(final UUID id) throws ObjectNotFoundException {
		final Challenge obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "Challenge");
		}

		return obj;
	}

	@Override
	public Challenge create(final Challenge obj) {
		return dao.save(obj);
	}

	@Override
	public Challenge save(final Challenge obj) throws ObjectNotFoundException {
		final Challenge current = get(obj.getId());

		if (obj.getDefaultConfidentialityLevel() == null) {
			obj.setDefaultConfidentialityLevel(null);
		} else {
			obj.setDefaultConfidentialityLevel(confidentialityLevelDao.load(obj
					.getDefaultConfidentialityLevel().getId()));
		}

		current.overwrite(obj);

		return dao.save(current);
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final Challenge current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	@Override
	public List<Challenge> challengeSearch(final String query) {
		final List<Challenge> challenges = dao.searchByQuery(query);
		final List<Challenge> results = Lists.newArrayList();

		for (Challenge challenge : challenges) {
			long count = challengeReferralService
					.countByChallengeIdNotOnActiveTaskList(challenge,
							securityService.currentUser().getPerson(),
							securityService.getSessionId());
			if (count > 0) {
				results.add(challenge);
			}
		}

		return results;
	}

	protected void setDao(final ChallengeDao dao) {
		this.dao = dao;
	}
}
