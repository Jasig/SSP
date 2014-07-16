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
package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.WatchStudentDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.WatchStudentTOFactory;
import org.jasig.ssp.model.WatchStudent;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.WatchStudentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * WatchStudent transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class WatchStudentTOFactoryImpl
		extends AbstractAuditableTOFactory<WatchStudentTO, WatchStudent>
		implements WatchStudentTOFactory {

	/**
	 * Construct an instance with the specific classes needed by super class
	 * methods.
	 */
	public WatchStudentTOFactoryImpl() {
		super(WatchStudentTO.class, WatchStudent.class);
	}

	@Autowired
	private transient WatchStudentDao dao;

	@Autowired
	private transient PersonService personService;

	@Override
	protected WatchStudentDao getDao() {
		return dao;
	}

	@Override
	public WatchStudent from(final WatchStudentTO tObject) throws ObjectNotFoundException {
		final WatchStudent model = super.from(tObject);
		model.setPerson(personService.get(tObject.getWatcherId()));
		model.setStudent(personService.get(tObject.getStudentId()));
		model.setWatchDate(tObject.getWatchDate());

		return model;
	}
}