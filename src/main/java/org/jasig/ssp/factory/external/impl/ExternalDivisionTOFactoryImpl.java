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
package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalDivisionDao;
import org.jasig.ssp.dao.external.ExternalProgramDao;
import org.jasig.ssp.factory.external.ExternalDivisionTOFactory;
import org.jasig.ssp.factory.external.ExternalProgramTOFactory;
import org.jasig.ssp.model.external.ExternalDivision;
import org.jasig.ssp.model.external.ExternalProgram;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalDivisionTO;
import org.jasig.ssp.transferobject.external.ExternalProgramTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Term transfer object factory implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class ExternalDivisionTOFactoryImpl
		extends AbstractExternalDataTOFactory<ExternalDivisionTO, ExternalDivision>
		implements ExternalDivisionTOFactory {

	public ExternalDivisionTOFactoryImpl() {
		super(ExternalDivisionTO.class, ExternalDivision.class);
	}

	@Autowired
	private transient ExternalDivisionDao dao;

	@Override
	protected ExternalDivisionDao getDao() {
		return dao;
	}

	@Override
	public ExternalDivisionTO from(final ExternalDivision tObject) {
		final ExternalDivisionTO model = super.from(tObject);

		model.setCode(tObject.getCode());
		model.setName(tObject.getName());
		return model;
	}
}