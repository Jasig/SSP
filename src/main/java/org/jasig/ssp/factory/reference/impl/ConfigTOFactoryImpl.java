/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
 * Config transfer object factory implementation class for converting back and
 * forth from Config models.
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

		/*
		 * Don't allow default value to be set with NEW data, so copy from
		 * previous value. Yes, this will fail for new objects. Do not use this
		 * implementation for creating new configuration values — it should be
		 * done in the liquibase scripts instead.
		 */
		try {
			final Config previous = dao.get(model.getId());
			model.setDefaultValue(previous.getDefaultValue());
		} catch (final ObjectNotFoundException exc) {
			// Provide more accurate error message;
			throw new ObjectNotFoundException(
					model.getId(),
					"Config",
					"Existing configuration value \""
							+ model.getName()
							+ "\" could not be found. New values can only be created via database scripts.",
					exc);
		}

		return model;
	}
}
