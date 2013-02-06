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

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.SelfHelpGuideQuestionDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideQuestionTOFactory;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideQuestionTO;

@Service
@Transactional(readOnly = true)
public class SelfHelpGuideQuestionTOFactoryImpl
		extends
		AbstractReferenceTOFactory<SelfHelpGuideQuestionTO, SelfHelpGuideQuestion>
		implements SelfHelpGuideQuestionTOFactory {

	public SelfHelpGuideQuestionTOFactoryImpl() {
		super(SelfHelpGuideQuestionTO.class, SelfHelpGuideQuestion.class);
	}

	@Autowired
	private transient SelfHelpGuideQuestionDao dao;

	@Override
	protected SelfHelpGuideQuestionDao getDao() {
		return dao;
	}

	@Override
	public SelfHelpGuideQuestion from(SelfHelpGuideQuestionTO tObject)
			throws ObjectNotFoundException {
		SelfHelpGuideQuestion question = super.from(tObject);
		question.setName("N/A");
		question.setQuestionNumber(tObject.getQuestionNumber());
		question.setMandatory(tObject.isMandatory());
		question.setCritical(tObject.isMandatory());
		question.setSelfHelpGuide(new SelfHelpGuide(UUID.fromString(tObject.getSelfHelpGuideId())));
		question.setChallenge(new Challenge((tObject.getChallengeId())));
		
		
		return question;
	}
}
