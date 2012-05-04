package org.jasig.ssp.factory.reference.impl;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.reference.EarlyAlertOutreachDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertOutreachTOFactory;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.EarlyAlertOutreachTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertOutreach transfer object factory implementation class for
 * converting back and forth from EarlyAlertOutreach models.
 * 
 * @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class EarlyAlertOutreachTOFactoryImpl extends
		AbstractReferenceTOFactory<EarlyAlertOutreachTO, EarlyAlertOutreach>
		implements EarlyAlertOutreachTOFactory {

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	public EarlyAlertOutreachTOFactoryImpl() {
		super(EarlyAlertOutreachTO.class, EarlyAlertOutreach.class);
	}

	@Autowired
	private transient EarlyAlertOutreachDao dao;

	@Override
	protected EarlyAlertOutreachDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertOutreach from(@NotNull final EarlyAlertOutreachTO tObject)
			throws ObjectNotFoundException {
		final EarlyAlertOutreach model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());

		return model;
	}
}
