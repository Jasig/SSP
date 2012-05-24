package org.jasig.ssp.service.reference.impl;

import org.codehaus.plexus.util.StringUtils;
import org.jasig.ssp.dao.reference.ConfigDao;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfigException;
import org.jasig.ssp.service.reference.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Configuration (Config) service implementation
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

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigServiceImpl.class);

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

	@Override
	public String getByNameNullOrDefaultValue(final String name) {
		final Config config = getByName(name);
		if (config == null) {
			return null;
		}
		if (StringUtils.isEmpty(config.getValue())) {
			LOGGER.warn("Using default value for:" + name);
			return config.getDefaultValue();
		}

		return config.getValue();
	}

	@Override
	public int getByNameExceptionOrDefaultAsInt(final String name) {
		final Config config = getByName(name);

		if (config == null) {
			throw new ConfigException(name);
		}
		if (StringUtils.isEmpty(config.getValue())
				|| !StringUtils.isNumeric(config.getValue())) {
			LOGGER.warn("Using default value for:" + name);
			if (StringUtils.isNumeric(config.getDefaultValue())) {
				return Integer.valueOf(config.getDefaultValue());
			} else {
				throw new ConfigException(name, "be numeric");
			}
		} else {
			return Integer.valueOf(config.getValue());
		}
	}
}