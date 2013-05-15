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

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.reference.TermNoteTOFactory;
import org.jasig.ssp.model.TermNote;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.TermNoteTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TermNoteTOFactoryImpl extends AbstractAuditableTOFactory<TermNoteTO, TermNote>
		implements TermNoteTOFactory {

	public TermNoteTOFactoryImpl() {
		super(TermNoteTO.class, TermNote.class);
	}
	

	@Override
	protected AuditableCrudDao<TermNote> getDao() {
		return null;
	}
	
	@Override
	public TermNote from(TermNoteTO tObject) throws ObjectNotFoundException {
		TermNote model = new TermNote();
		model.setContactNotes(tObject.getContactNotes());
		model.setIsImportant(tObject.getIsImportant());
		model.setStudentNotes(tObject.getStudentNotes());
		model.setTermCode(tObject.getTermCode());
		return model;
	}

}