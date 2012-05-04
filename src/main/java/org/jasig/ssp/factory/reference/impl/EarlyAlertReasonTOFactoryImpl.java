package org.jasig.ssp.factory.reference.impl;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.reference.EarlyAlertReasonDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertReasonTOFactory;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.EarlyAlertReasonTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertReason transfer object factory implementation class for converting
 * back and forth from EarlyAlertReason models.
 * 
 * @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class EarlyAlertReasonTOFactoryImpl extends
		AbstractReferenceTOFactory<EarlyAlertReasonTO, EarlyAlertReason>
		implements EarlyAlertReasonTOFactory {

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	public EarlyAlertReasonTOFactoryImpl() {
		super(EarlyAlertReasonTO.class, EarlyAlertReason.class);
	}

	@Autowired
	private transient EarlyAlertReasonDao dao;

	@Override
	protected EarlyAlertReasonDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertReason from(@NotNull final EarlyAlertReasonTO tObject)
			throws ObjectNotFoundException {
		final EarlyAlertReason model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());

		return model;
	}
}
