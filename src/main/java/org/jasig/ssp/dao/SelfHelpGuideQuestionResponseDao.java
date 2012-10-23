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
package org.jasig.ssp.dao;

import java.util.List;

import org.jasig.ssp.model.SelfHelpGuideQuestionResponse;
import org.jasig.ssp.security.SspUser;
import org.springframework.stereotype.Repository;

/**
 * SelfHelpGuideQuestionResponse data access methods
 */
@Repository
public class SelfHelpGuideQuestionResponseDao extends
		AbstractAuditableCrudDao<SelfHelpGuideQuestionResponse> implements
		AuditableCrudDao<SelfHelpGuideQuestionResponse> {

	/**
	 * Construct an instance with the specific types for use by super class
	 * methods.
	 */
	public SelfHelpGuideQuestionResponseDao() {
		super(SelfHelpGuideQuestionResponse.class);
	}

	/**
	 * Find all critical questions answered in the Self-Help Guide, by any
	 * non-anonymous users, that have not been sent.
	 * 
	 * @return List of SelfHelpGuideQuestionResponse responses that need Early
	 *         Alerts sent
	 */
	@SuppressWarnings(UNCHECKED)
	public List<SelfHelpGuideQuestionResponse> criticalResponsesForEarlyAlert() {
		return sessionFactory.getCurrentSession()
				.createQuery("from SelfHelpGuideQuestionResponse " +
						"where earlyAlertSent = false " +
						"and response = true " +
						"and selfHelpGuideQuestion.critical = true " +
						"and selfHelpGuideResponse.person.id != ?")
				.setParameter(0, SspUser.ANONYMOUS_PERSON_ID)
				.list();
	}
}
