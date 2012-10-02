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

import org.jasig.ssp.dao.reference.ChallengeDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ChallengeTOFactory;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Challenge transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class ChallengeTOFactoryImpl extends
		AbstractReferenceTOFactory<ChallengeTO, Challenge>
		implements ChallengeTOFactory {

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	public ChallengeTOFactoryImpl() {
		super(ChallengeTO.class, Challenge.class);
	}

	@Autowired
	private transient ChallengeDao dao;

	@Override
	protected ChallengeDao getDao() {
		return dao;
	}

	@Override
	public Challenge from(final ChallengeTO tObject)
			throws ObjectNotFoundException {
		final Challenge model = super.from(tObject);

		model.setSelfHelpGuideQuestion(tObject.getSelfHelpGuideQuestion());
		model.setSelfHelpGuideDescription(tObject.getSelfHelpGuideDescription());
		model.setShowInSelfHelpSearch(tObject.isShowInSelfHelpSearch());
		model.setShowInStudentIntake(tObject.isShowInStudentIntake());
		model.setTags(tObject.getTags());

		if (tObject
				.getDefaultConfidentialityLevelId() == null) {
			model.setDefaultConfidentialityLevel(null);
		} else {
			try {
				model.setDefaultConfidentialityLevel(confidentialityLevelService
						.get(tObject
								.getDefaultConfidentialityLevelId()));
			} catch (final ObjectNotFoundException exc) {
				throw new ObjectNotFoundException(
						tObject.getDefaultConfidentialityLevelId(),
						"ConfidentialityLevel",
						"Invalid confidentiality level for this challenge.",
						exc);
			}
		}

		return model;
	}
}