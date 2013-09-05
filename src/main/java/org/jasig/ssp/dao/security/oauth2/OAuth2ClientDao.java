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
package org.jasig.ssp.dao.security.oauth2;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AbstractAuditableCrudDao;
import org.jasig.ssp.model.security.oauth2.OAuth2Client;
import org.springframework.stereotype.Repository;

@Repository
public class OAuth2ClientDao extends AbstractAuditableCrudDao<OAuth2Client> {

	protected OAuth2ClientDao() {
		super(OAuth2Client.class);
	}

	public OAuth2Client findByClientId(String clientId) {
		if (StringUtils.isBlank(StringUtils.lowerCase(clientId))) {
			throw new IllegalArgumentException("username can not be empty.");
		}

		final Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(OAuth2Client.class);
		query.add(Restrictions.eq(OAuth2Client.getPhysicalClientIdColumnName(), clientId));
		return (OAuth2Client) query.uniqueResult();
	}

}
