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

import org.jasig.ssp.dao.reference.ChallengeReferralDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ChallengeReferralTOFactory;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.ChallengeReferralTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ChallengeReferral transfer object factory
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional(readOnly = true)
public class ChallengeReferralTOFactoryImpl extends
		AbstractReferenceTOFactory<ChallengeReferralTO, ChallengeReferral>
		implements ChallengeReferralTOFactory {

	/**
	 * Construct an instance with the specific types that the base methods use.
	 */
	public ChallengeReferralTOFactoryImpl() {
		super(ChallengeReferralTO.class, ChallengeReferral.class);
	}

	@Autowired
	private transient ChallengeReferralDao dao;

	@Override
	protected ChallengeReferralDao getDao() {
		return dao;
	}

	@Override
	public ChallengeReferral from(final ChallengeReferralTO tObject)
			throws ObjectNotFoundException {
		final ChallengeReferral model = super.from(tObject);

		model.setPublicDescription(tObject.getPublicDescription());
		model.setShowInSelfHelpGuide(tObject.getShowInSelfHelpGuide());
		model.setShowInStudentIntake(tObject.getShowInStudentIntake());

		return model;
	}
}
