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

import org.jasig.ssp.dao.reference.StudentTypeDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.StudentTypeTOFactory;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.StudentTypeTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * StudentType transfer object factory implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional(readOnly = true)
public class StudentTypeTOFactoryImpl
		extends
		AbstractReferenceTOFactory<StudentTypeTO, StudentType>
		implements StudentTypeTOFactory {

	public StudentTypeTOFactoryImpl() {
		super(StudentTypeTO.class,
				StudentType.class);
	}

	@Autowired
	private transient StudentTypeDao dao;

	@Override
	protected StudentTypeDao getDao() {
		return dao;
	}

	@Override
	public StudentType from(final StudentTypeTO tObject) throws ObjectNotFoundException {
		StudentType model = super.from(tObject);
		model.setRequireInitialAppointment(tObject.isRequireInitialAppointment());
		model.setCode(tObject.getCode());
		return model;
	}
}