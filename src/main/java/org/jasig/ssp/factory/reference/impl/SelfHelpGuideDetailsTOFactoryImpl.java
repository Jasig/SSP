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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.mygps.model.transferobject.SelfHelpGuideQuestionTO;
import org.jasig.ssp.dao.reference.SelfHelpGuideDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideDetailsTOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideDetailsTO;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideTO;

@Service
@Transactional(readOnly = true)
public class SelfHelpGuideDetailsTOFactoryImpl extends
		AbstractReferenceTOFactory<SelfHelpGuideDetailsTO, SelfHelpGuide>
		implements SelfHelpGuideDetailsTOFactory {

	public SelfHelpGuideDetailsTOFactoryImpl() {
		super(SelfHelpGuideDetailsTO.class, SelfHelpGuide.class);
	}

	@Autowired
	private transient SelfHelpGuideDao dao;

	@Override
	protected SelfHelpGuideDao getDao() {
		return dao;
	}
	
	@Override
	public SelfHelpGuide from(SelfHelpGuideDetailsTO tObject)
			throws ObjectNotFoundException {
		SelfHelpGuide guide = super.from(tObject);
		
		guide.setThreshold(tObject.getThreshold());
		guide.setIntroductoryText(tObject.getIntroductoryText());
		guide.setSummaryText(tObject.getSummaryText());
		guide.setSummaryTextEarlyAlert(tObject.getSummaryTextEarlyAlert());
		guide.setSummaryTextThreshold(tObject.getSummaryTextThreshold());
		guide.setObjectStatus(tObject.isActive() ? ObjectStatus.ACTIVE : ObjectStatus.INACTIVE);
		guide.setAuthenticationRequired(tObject.isAuthenticationRequired());
		return guide;
	}

	@Override
	protected SelfHelpGuideDetailsTO newTObject() {
		return super.newTObject();
	}
}
