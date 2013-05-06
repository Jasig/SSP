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

import org.jasig.ssp.dao.external.ExternalDepartmentDao;
import org.jasig.ssp.factory.external.ExternalDepartmentTOFactory;
import org.jasig.ssp.model.external.ExternalDepartment;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalDepartmentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Term transfer object factory implementation
 * 
 * @author tony.arland
 */
@Service
@Transactional(readOnly = true)
public class ExternalDepartmentTOFactoryImplementation
		extends AbstractExternalDataTOFactory<ExternalDepartmentTO, ExternalDepartment>
		implements ExternalDepartmentTOFactory {
 
	public ExternalDepartmentTOFactoryImplementation() {
		super(ExternalDepartmentTO.class, ExternalDepartment.class);
	}

	@Autowired
	private transient ExternalDepartmentDao dao;

	@Override
	protected ExternalDepartmentDao getDao() {
		return dao;
	}

	@Override
	public ExternalDepartment from(final ExternalDepartmentTO tObject) throws ObjectNotFoundException {
		final ExternalDepartment model = super.from(tObject);

		model.setCode(tObject.getCode());
		model.setName(tObject.getName());
		return model;
	}
}