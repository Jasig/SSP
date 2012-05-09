package org.jasig.ssp.factory.impl;

import java.util.HashSet;
import java.util.UUID;

import org.jasig.ssp.dao.EarlyAlertResponseDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.EarlyAlertResponseTOFactory;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.EarlyAlertOutreachService;
import org.jasig.ssp.transferobject.EarlyAlertResponseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertResponse transfer object factory
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional(readOnly = true)
public class EarlyAlertResponseTOFactoryImpl extends
		AbstractAuditableTOFactory<EarlyAlertResponseTO, EarlyAlertResponse>
		implements EarlyAlertResponseTOFactory {

	/**
	 * Construct an EarlyAlertResponse transfer object factory with the specific
	 * class types for use by the super class methods.
	 */
	public EarlyAlertResponseTOFactoryImpl() {
		super(EarlyAlertResponseTO.class, EarlyAlertResponse.class);
	}

	@Autowired
	private transient EarlyAlertResponseDao dao;

	@Autowired
	private transient EarlyAlertService earlyAlertService;

	@Autowired
	private transient EarlyAlertOutreachService earlyAlertOutreachService;

	@Autowired
	private transient EarlyAlertOutcomeService earlyAlertOutcomeService;

	@Override
	protected EarlyAlertResponseDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertResponse from(final EarlyAlertResponseTO tObject)
			throws ObjectNotFoundException {
		final EarlyAlertResponse model = super.from(tObject);

		model.setComment(tObject.getComment());
		model.setEarlyAlertOutcomeOtherDescription(tObject
				.getEarlyAlertOutcomeOtherDescription());

		if (tObject.getEarlyAlertId() != null) {
			model.setEarlyAlert(earlyAlertService.get(tObject.getEarlyAlertId()));
		}

		model.setEarlyAlertOutreachIds(new HashSet<EarlyAlertOutreach>());
		if (tObject.getEarlyAlertOutreachIds() != null) {
			for (UUID obj : tObject.getEarlyAlertOutreachIds()) {
				model.getEarlyAlertOutreachIds().add(
						earlyAlertOutreachService.load(obj));
			}
		}

		model.setEarlyAlertOutcomeIds(new HashSet<EarlyAlertOutcome>());
		if (tObject.getEarlyAlertOutcomeIds() != null) {
			for (UUID obj : tObject.getEarlyAlertOutcomeIds()) {
				model.getEarlyAlertOutcomeIds().add(
						earlyAlertOutcomeService.load(obj));
			}
		}

		return model;
	}
}
