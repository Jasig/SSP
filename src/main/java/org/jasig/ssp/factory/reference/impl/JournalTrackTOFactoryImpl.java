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

import org.jasig.ssp.dao.reference.JournalTrackDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.JournalTrackTOFactory;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.JournalTrackTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class JournalTrackTOFactoryImpl extends
		AbstractReferenceTOFactory<JournalTrackTO, JournalTrack>
		implements JournalTrackTOFactory {

	public JournalTrackTOFactoryImpl() {
		super(JournalTrackTO.class, JournalTrack.class);
	}

	@Autowired
	private transient JournalTrackDao dao;

	@Override
	protected JournalTrackDao getDao() {
		return dao;
	}

	@Override
	public JournalTrack from(final JournalTrackTO tObject)
			throws ObjectNotFoundException {
		final JournalTrack model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());

		return model;
	}

}
