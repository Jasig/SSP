/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.impl;

import java.util.List;

import org.jasig.ssp.dao.PersonChallengeDao;
import org.jasig.ssp.model.PersonChallenge;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonChallengeService;
import org.jasig.ssp.transferobject.reference.ReferenceCounterTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.transferobject.reports.StudentChallengesTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonChallengeServiceImpl extends
		AbstractPersonAssocAuditableService<PersonChallenge> implements
		PersonChallengeService {

	@Autowired
	private transient PersonChallengeDao dao;

	@Override
	protected PersonChallengeDao getDao() {
		return dao;
	}
	
	public List<StudentChallengesTO> getStudentChallenges(PersonSearchFormTO form,
			final SortingAndPaging sAndP) throws ObjectNotFoundException{
		return dao.getStudentChallenges(form, sAndP);
	}
	
	public List<ReferenceCounterTO> getStudentChallengesCount(PersonSearchFormTO form) throws ObjectNotFoundException {
		return dao.getStudentChallengesCount(form);
	}

}
