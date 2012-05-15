package org.jasig.ssp.factory.reference.impl;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.reference.ConfigDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ConfigTOFactory;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.ConfigTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Config transfer object factory implementation class for converting
 * back and forth from Config models.
 * 
 * @author daniel.bower
 */
@Service
@Transactional(readOnly = true)
public class ConfigTOFactoryImpl extends
		AbstractReferenceTOFactory<ConfigTO, Config>
		implements ConfigTOFactory {

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	public ConfigTOFactoryImpl() {
		super(ConfigTO.class, Config.class);
	}

	@Autowired
	private transient ConfigDao dao;

	@Override
	protected ConfigDao getDao() {
		return dao;
	}

	@Override
	public Config from(@NotNull final ConfigTO tObject)
			throws ObjectNotFoundException {
		final Config model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());
		model.setValue(tObject.getValue());
		model.setValueValidation(tObject.getValueValidation());

		return model;
	}
}
