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
package org.jasig.ssp.dao.security.lti;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AbstractAuditableCrudDao;
import org.jasig.ssp.model.security.lti.LtiConsumer;
import org.springframework.stereotype.Repository;

@Repository
public class LtiConsumerDao extends AbstractAuditableCrudDao<LtiConsumer> {

	protected LtiConsumerDao() {
		super(LtiConsumer.class);
	}

	
	public LtiConsumer findByConsumerKey(String consumerKey) {
		if (StringUtils.isBlank(consumerKey)) {
			throw new IllegalArgumentException("Consumer Key can not be empty.");
		}
		final Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(LtiConsumer.class);
		query.add(Restrictions.eq("username", consumerKey));
		
		return (LtiConsumer) query.uniqueResult();
	}

}
