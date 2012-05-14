package org.jasig.ssp.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.jasig.ssp.dao.EarlyAlertResponseDao;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.model.reference.EarlyAlertReferral;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.EarlyAlertOutreachService;
import org.jasig.ssp.service.reference.EarlyAlertReferralService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertResponse service implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional
public class EarlyAlertResponseServiceImpl extends
		AbstractAuditableCrudService<EarlyAlertResponse>
		implements EarlyAlertResponseService {

	@Autowired
	private transient EarlyAlertResponseDao dao;

	@Autowired
	private transient EarlyAlertOutcomeService earlyAlertOutcomeService;

	@Autowired
	private transient EarlyAlertOutreachService earlyAlertOutreachService;

	@Autowired
	private transient EarlyAlertReferralService earlyAlertReferralService;

	@Autowired
	private transient EarlyAlertService earlyAlertService;

	@Override
	protected EarlyAlertResponseDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertResponse save(final EarlyAlertResponse obj)
			throws ObjectNotFoundException {
		final EarlyAlertResponse current = getDao().get(obj.getId());

		current.setEarlyAlertOutcomeOtherDescription(obj
				.getEarlyAlertOutcomeOtherDescription());
		current.setComment(obj.getComment());

		if (obj.getEarlyAlert() == null) {
			current.setEarlyAlert(null);
		} else {
			current.setEarlyAlert(earlyAlertService.get(obj.getEarlyAlert()
					.getId()));
		}

		if (obj.getEarlyAlertOutcome() == null) {
			current.setEarlyAlertOutcome(null);
		} else {
			current.setEarlyAlertOutcome(earlyAlertOutcomeService.get(obj
					.getEarlyAlertOutcome().getId()));
		}

		final Set<EarlyAlertOutreach> earlyAlertOutreachs = new HashSet<EarlyAlertOutreach>();
		if (obj.getEarlyAlertOutreachIds() != null) {
			for (EarlyAlertOutreach outreach : obj.getEarlyAlertOutreachIds()) {
				earlyAlertOutreachs.add(earlyAlertOutreachService.load(outreach
						.getId()));
			}
		}

		current.setEarlyAlertOutreachIds(earlyAlertOutreachs);

		final Set<EarlyAlertReferral> earlyAlertReferrals = new HashSet<EarlyAlertReferral>();
		if (obj.getEarlyAlertReferralIds() != null) {
			for (EarlyAlertReferral outcome : obj.getEarlyAlertReferralIds()) {
				earlyAlertReferrals.add(earlyAlertReferralService.load(outcome
						.getId()));
			}
		}

		current.setEarlyAlertReferralIds(earlyAlertReferrals);

		return getDao().save(current);
	}

	@Override
	public PagingWrapper<EarlyAlertResponse> getAllForPerson(
			final Person person,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
	}
}
