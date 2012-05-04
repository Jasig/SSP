package org.jasig.ssp.factory.reference.impl;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.reference.EarlyAlertSuggestionDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertSuggestionTOFactory;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.EarlyAlertSuggestionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertSuggestion transfer object factory implementation class for
 * converting back and forth from EarlyAlertSuggestion models.
 * 
 * @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class EarlyAlertSuggestionTOFactoryImpl
		extends
		AbstractReferenceTOFactory<EarlyAlertSuggestionTO, EarlyAlertSuggestion>
		implements EarlyAlertSuggestionTOFactory {

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	public EarlyAlertSuggestionTOFactoryImpl() {
		super(EarlyAlertSuggestionTO.class, EarlyAlertSuggestion.class);
	}

	@Autowired
	private transient EarlyAlertSuggestionDao dao;

	@Override
	protected EarlyAlertSuggestionDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertSuggestion from(
			@NotNull final EarlyAlertSuggestionTO tObject)
			throws ObjectNotFoundException {
		final EarlyAlertSuggestion model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());

		return model;
	}
}
