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

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalPersonNoteDao;
import org.jasig.ssp.factory.external.ExternalPersonNoteTOFactory;
import org.jasig.ssp.model.external.ExternalPersonNote;
import org.jasig.ssp.transferobject.external.ExternalPersonNoteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExternalPersonNoteTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalPersonNoteTO, ExternalPersonNote>
		implements ExternalPersonNoteTOFactory {

	
	@Autowired
	private transient ExternalPersonNoteDao dao;
	
	public ExternalPersonNoteTOFactoryImpl() {
		super(ExternalPersonNoteTO.class, ExternalPersonNote.class);
	}
	
	@Override
	protected ExternalDataDao<ExternalPersonNote> getDao() {
		return dao;
	}
	
	@Override
	public ExternalPersonNoteTO from(ExternalPersonNote tObject) {
		final ExternalPersonNoteTO model = super.from(tObject);
		model.setAuthor(tObject.getAuthor());
		model.setCode(tObject.getCode());
		model.setDateNoteTaken(tObject.getDateNoteTaken());
		model.setDepartment(tObject.getDepartment());
		model.setNote(tObject.getNote());
		model.setNoteType(tObject.getNoteType());
		model.setSchoolId(tObject.getSchoolId());
		return model;
	}
}
