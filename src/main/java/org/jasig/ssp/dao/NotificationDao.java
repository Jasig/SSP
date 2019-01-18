/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.jasig.ssp.model.Notification;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.NotificationReadStatus;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;


/**
 * DAO for the {@link Notification} model
 */
@Repository
public class NotificationDao extends AbstractAuditableCrudDao<Notification> implements AuditableCrudDao<Notification> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationDao.class);

    @Autowired
	private transient ConfigService configService;


	/**
	 * Constructor that initializes the public class NotificationDao extends AbstractReferenceAuditableCrudDao<Notification>
		implements AuditableCrudDao<Notification> { instance with the specific class types
	 * for super class method use.
	 */
	public NotificationDao() {
		super(Notification.class);
	}

	//TODO Need to remove NotificationRecipient Dao or this and combine into one?

	public PagingWrapper<Notification> getNotifications(UUID personId, String sspRole,
														NotificationReadStatus notificationReadStatus,
														SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria(sAndP);
		criteria.createAlias("notificationRecipients", "notificationRecipients");
		if (personId!=null) {
			criteria.add(Restrictions.eq("notificationRecipients.person.id", personId));
		}
		if (sspRole!=null) {
			criteria.add(Restrictions.eq("notificationRecipients.sspRole", sspRole));
		}
		criteria.add(Restrictions.eq("notificationRecipients.objectStatus", ObjectStatus.ACTIVE));

		if (notificationReadStatus==NotificationReadStatus.READ) {
			criteria.createAlias("notificationReads", "notificationReads");
			criteria.add(Restrictions.eq("notificationReads.objectStatus", ObjectStatus.INACTIVE));
		} else if (notificationReadStatus==NotificationReadStatus.UNREAD){
			criteria.createAlias("notificationReads", "notificationReads", JoinType.LEFT_OUTER_JOIN);
			criteria.add(Restrictions.or(
					Restrictions.eq("notificationReads.objectStatus", ObjectStatus.ACTIVE),
					Restrictions.isNull("notificationReads.id")));
		}

		return processCriteriaWithStatusSortingAndPaging(criteria, sAndP, Criteria.DISTINCT_ROOT_ENTITY);
	}
}
