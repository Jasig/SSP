package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.ConfigDao;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.service.reference.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Config service implementation
 * 
 * @author daniel.bower
 */
@Service
@Transactional
public class ConfigServiceImpl extends
		AbstractReferenceService<Config>
		implements ConfigService {

	@Autowired
	transient private ConfigDao dao;

	/**
	 * Constructor that sets the specific class types to be used by base class
	 * methods.
	 */
	public ConfigServiceImpl() {
		super();
	}

	/**
	 * Set the DAO instance
	 * 
	 * @param dao
	 *            The DAO instance
	 */
	protected void setDao(final ConfigDao dao) {
		this.dao = dao;
	}

	@Override
	protected ConfigDao getDao() {
		return dao;
	}
}
