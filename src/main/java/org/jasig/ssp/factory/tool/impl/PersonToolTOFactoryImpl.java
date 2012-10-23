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
package org.jasig.ssp.factory.tool.impl;

import org.codehaus.plexus.util.StringUtils;
import org.jasig.ssp.dao.tool.PersonToolDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.tool.PersonToolTOFactory;
import org.jasig.ssp.model.tool.PersonTool;
import org.jasig.ssp.model.tool.Tools;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.tool.PersonToolTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonToolTOFactoryImpl
		extends AbstractAuditableTOFactory<PersonToolTO, PersonTool>
		implements PersonToolTOFactory {

	public PersonToolTOFactoryImpl() {
		super(PersonToolTO.class, PersonTool.class);
	}

	@Autowired
	private transient PersonToolDao dao;

	@Override
	protected PersonToolDao getDao() {
		return dao;
	}

	@Override
	public PersonTool from(final PersonToolTO tObject)
			throws ObjectNotFoundException {
		final PersonTool model = super.from(tObject);

		if (StringUtils.isEmpty(tObject.getToolCode())) {
			model.setTool(null);
		} else {
			model.setTool(Tools.valueOf(tObject.getToolCode()));
		}
		return model;
	}

}
