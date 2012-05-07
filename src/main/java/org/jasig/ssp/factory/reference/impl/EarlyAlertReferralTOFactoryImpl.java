package org.jasig.ssp.factory.reference.impl;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.reference.EarlyAlertReferralDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertReferralTOFactory;
import org.jasig.ssp.model.reference.EarlyAlertReferral;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.EarlyAlertReferralTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertReferral transfer object factory implementation class for
 * converting back and forth from EarlyAlertReferral models.
 * 
 * @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class EarlyAlertReferralTOFactoryImpl
		extends
		AbstractReferenceTOFactory<EarlyAlertReferralTO, EarlyAlertReferral>
		implements EarlyAlertReferralTOFactory {

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	public EarlyAlertReferralTOFactoryImpl() {
		super(EarlyAlertReferralTO.class, EarlyAlertReferral.class);
	}

	@Autowired
	private transient EarlyAlertReferralDao dao;

	@Override
	protected EarlyAlertReferralDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertReferral from(
			@NotNull final EarlyAlertReferralTO tObject)
			throws ObjectNotFoundException {
		final EarlyAlertReferral model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());
		model.setAcronym(tObject.getAcronym());

		return model;
	}
}
