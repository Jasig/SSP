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

package org.jasig.ssp.dao.security.oauth;


import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AbstractDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.security.oauth.OAuth1Nonce;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;


@Repository
public class OAuth1NonceDao extends AbstractDao<OAuth1Nonce> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OAuth1NonceDao.class);

	private static final String NONCE_TIMESTAMP_PARAM = "nonceTimestamp";
	private static final String DELETE_WITH_TIMESTAMP_EARLIER_THAN =
			"delete from OAuth1Nonce where nonceTimestamp < :" + NONCE_TIMESTAMP_PARAM;

	protected OAuth1NonceDao() {
		super(OAuth1Nonce.class);
	}

	@Override
	public PagingWrapper<OAuth1Nonce> getAll(final ObjectStatus status) {
		// We don't support ObjectStatus on nonces and should never need to do this anyway.
		// And it's potentially a high-contention table, so lets not run the risk of leaking
		// data, leaking memory, or otherwise creating undue bottlenecks/loads
		throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public PagingWrapper<OAuth1Nonce> getAll(final SortingAndPaging status) {
		// We don't support SortingAndPaging on nonces and should never need to do this anyway.
		// And it's potentially a high-contention table, so lets not run the risk of leaking
		// data, leaking memory, or otherwise creating undue bottlenecks/loads
		throw new UnsupportedOperationException("Not implemented.");
	}

	public OAuth1Nonce get(String consumerKey, long timestamp, String nonce) {
		if ( StringUtils.isBlank(nonce) || StringUtils.isBlank(consumerKey) ) {
			throw new IllegalArgumentException("nonce or consumer key can not be empty.");
		}

		final Criteria query = sessionFactory.getCurrentSession().createCriteria(OAuth1Nonce.class);
		final LinkedHashMap<String,Object> predicates = Maps.newLinkedHashMap();
		predicates.put("consumerKey", consumerKey);
		predicates.put("nonceTimestamp", timestamp);
		predicates.put("nonce", nonce);
		query.add(Restrictions.allEq(predicates));

		return (OAuth1Nonce) query.uniqueResult();
	}

	public OAuth1Nonce save(OAuth1Nonce obj) {
		final Session currentSession = sessionFactory.getCurrentSession();

		currentSession.save(obj);
		currentSession.flush(); // make sure constraint violations are checked now

		return obj;
	}

	public void delete(OAuth1Nonce obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}

	public int deleteWithTimestampEarlierThan(long cutoff) {
		final Query query = sessionFactory.getCurrentSession()
				.createQuery(DELETE_WITH_TIMESTAMP_EARLIER_THAN);
		query.setLong(NONCE_TIMESTAMP_PARAM, cutoff);
		return query.executeUpdate();
	}
}
