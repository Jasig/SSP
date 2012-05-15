package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.ConfigDao;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Config service implementation
 * 
 */
@Service
@Transactional
public class ConfigServiceImpl extends
		AbstractReferenceService<Config>
		implements ConfigService {

	@Autowired
	transient private ConfigDao dao;

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

	private Config getByName(final String name) {
		return dao.getByName(name);
	}

	@Override
	public String getByNameEmpty(final String name) {
		final Config config = getByName(name);
		if ((config == null) || (config.getValue() == null)) {
			return "";
		}
		return config.getValue();
	}

	@Override
	public String getByNameException(final String name)
			throws ObjectNotFoundException {
		final Config config = getByName(name);
		if (config == null) {
			throw new ObjectNotFoundException(
					"Could not find Config value with key: " + name, "Config");
		}

		if (config.getValue() == null) {
			throw new ObjectNotFoundException(
					"Value not set for key: " + name, "Config");

		}
		return config.getValue();
	}

	@Override
	public String getByNameNull(final String name) {
		final Config config = getByName(name);
		if ((config == null) || (config.getValue() == null)) {
			return null;
		}
		return config.getValue();
	}
}
