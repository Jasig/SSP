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
package org.jasig.ssp.service.impl; // NOPMD

import org.jasig.ssp.dao.NotificationDao;
import org.jasig.ssp.model.Notification;
import org.jasig.ssp.service.NotificationService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Notification service implementation for sending notifications.
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private transient NotificationDao notificationDao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient ConfigService configService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NotificationServiceImpl.class);


	//TODO finish create service and retrieve service below.
	//  In add need to figure out SSP Role discovery?


	@Override
	@Transactional(readOnly = true)
	public PagingWrapper<Notification> getNotifications(SortingAndPaging sortingAndPaging) {
		return null;
	}
}
