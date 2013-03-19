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
package org.jasig.ssp.service;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.transferobject.SelfHelpGuideResponseTO;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface PersonSelfHelpGuideResponseService
		extends PersonAssocAuditableService<SelfHelpGuideResponse> {

	SelfHelpGuideResponse initiateSelfHelpGuideResponse(
			SelfHelpGuide selfHelpGuide,
			Person person, String sessionId)
			throws ObjectNotFoundException;

	boolean answerSelfHelpGuideQuestion(
			final SelfHelpGuideResponse selfHelpGuideResponse,
			final SelfHelpGuideQuestion selfHelpGuideQuestion,
			final Boolean response)
			throws ObjectNotFoundException;

	boolean completeSelfHelpGuideResponse(
			SelfHelpGuideResponse selfHelpGuideResponse)
			throws ObjectNotFoundException;

	boolean cancelSelfHelpGuideResponse(
			SelfHelpGuideResponse selfHelpGuideResponse)
			throws ObjectNotFoundException;

	SelfHelpGuideResponseTO getSelfHelpGuideResponseFor(
			SelfHelpGuideResponse selfHelpGuideResponse,
			SortingAndPaging referralSAndP)
			throws ObjectNotFoundException;
}
