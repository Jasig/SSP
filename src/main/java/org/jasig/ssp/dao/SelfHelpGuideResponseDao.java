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

import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.jasig.ssp.security.SspUser;
import org.springframework.stereotype.Repository;

/**
 * SelfHelpGuideResponse data access methods
 */
@Repository
public class SelfHelpGuideResponseDao
		extends AbstractPersonAssocAuditableCrudDao<SelfHelpGuideResponse>
		implements PersonAssocAuditableCrudDao<SelfHelpGuideResponse> {

	/**
	 * Construct an instance with the specific types for use by super class
	 * methods.
	 */
	public SelfHelpGuideResponseDao() {
		super(SelfHelpGuideResponse.class);
	}

	/**
	 * Get all SelfHelpGuideResponses, for all non-anonymous users, that exceed
	 * the defined threshold.
	 * 
	 * @return List of all applicable SelfHelpGuideResponses, for all
	 *         non-anonymous users.
	 */
	@SuppressWarnings(UNCHECKED)
	public List<SelfHelpGuideResponse> forEarlyAlert() {
		return sessionFactory.getCurrentSession()
				.createQuery("from SelfHelpGuideResponse shgr " +
						"where shgr.selfHelpGuide.threshold > 0 " +
						"and shgr.selfHelpGuide.threshold < " +
						"(select count(*) " +
						"from SelfHelpGuideQuestionResponse " +
						"where response = true " +
						"and selfHelpGuideResponse.id = shgr.id) " +
						"and shgr.person.id != ?")
				.setParameter(0, SspUser.ANONYMOUS_PERSON_ID)
				.list();
	}
}
