package org.jasig.ssp.factory.reference.impl;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.reference.EarlyAlertOutcomeDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertOutcomeTOFactory;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.EarlyAlertOutcomeTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertOutcome transfer object factory implementation class for converting
 * back and forth from EarlyAlertOutcome models.
 * 
 * @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class EarlyAlertOutcomeTOFactoryImpl extends
		AbstractReferenceTOFactory<EarlyAlertOutcomeTO, EarlyAlertOutcome>
		implements EarlyAlertOutcomeTOFactory {

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	public EarlyAlertOutcomeTOFactoryImpl() {
		super(EarlyAlertOutcomeTO.class, EarlyAlertOutcome.class);
	}

	@Autowired
	private transient EarlyAlertOutcomeDao dao;

	@Override
	protected EarlyAlertOutcomeDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertOutcome from(@NotNull final EarlyAlertOutcomeTO tObject)
			throws ObjectNotFoundException {
		final EarlyAlertOutcome model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());

		return model;
	}
}
