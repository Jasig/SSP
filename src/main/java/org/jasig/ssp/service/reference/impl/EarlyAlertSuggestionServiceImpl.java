package org.jasig.ssp.service.reference.impl;

import java.util.UUID;

import org.jasig.ssp.dao.reference.EarlyAlertSuggestionDao;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.service.reference.EarlyAlertSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertSuggestion service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class EarlyAlertSuggestionServiceImpl extends
		AbstractReferenceService<EarlyAlertSuggestion>
		implements EarlyAlertSuggestionService {

	@Autowired
	transient private EarlyAlertSuggestionDao dao;

	/**
	 * Constructor that sets the specific class types to be used by base class
	 * methods.
	 */
	public EarlyAlertSuggestionServiceImpl() {
		super();
	}

	/**
	 * Set the DAO instance
	 * 
	 * @param dao
	 *            The DAO instance
	 */
	protected void setDao(final EarlyAlertSuggestionDao dao) {
		this.dao = dao;
	}

	@Override
	protected EarlyAlertSuggestionDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertSuggestion load(final UUID id) {
		return dao.load(id);
	}
}
