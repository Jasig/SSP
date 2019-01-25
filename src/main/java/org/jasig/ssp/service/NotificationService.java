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
package org.jasig.ssp.service;

import org.jasig.ssp.model.Notification;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.NotificationCategory;
import org.jasig.ssp.model.reference.NotificationPriority;
import org.jasig.ssp.model.reference.NotificationReadStatus;
import org.jasig.ssp.model.reference.SspRole;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Notification service
 */
public interface NotificationService {

    Notification getNotification(UUID id) throws ObjectNotFoundException;

    /**
     * Returns all notifications based on sortingAndPaging
     * @param sortingAndPaging
     * @return
     */
    PagingWrapper<Notification> getNotifications(final SortingAndPaging sortingAndPaging);

    /**
     * Returns all notifications based on sortingAndPaging
     * @param sortingAndPaging
     * @param person
     * @param notificationReadStatus
     * @return
     */
    PagingWrapper<Notification> getNotifications(final Person person,
                                                 final NotificationReadStatus notificationReadStatus,
                                                 final SortingAndPaging sortingAndPaging);

    /**
     * Returns all notifications based on sortingAndPaging
     * @param sortingAndPaging
     * @param sspRole
     * @param notificationReadStatus
     * @return
     */
    PagingWrapper<Notification> getNotifications(final SspRole sspRole,
                                                 final NotificationReadStatus notificationReadStatus,
                                                 final SortingAndPaging sortingAndPaging);

    /**
     * Returns all notifications based on sortingAndPaging
     * @param sortingAndPaging
     * @param person
     * @param sspRole
     * @param notificationReadStatus
     * @return
     */
    PagingWrapper<Notification> getNotifications(final Person person, final SspRole sspRole,
                                                 final NotificationReadStatus notificationReadStatus,
                                                 final SortingAndPaging sortingAndPaging);

    /**
     * Creates new notification
     * @param subject
     * @param body
     * @param expirationDate
     * @param notificationPriority
     * @param notificationCategory
     * @param persons
     * @param sspRoles
     * @return
     */
    Notification create(final String subject, final String body, final Date expirationDate,
                        final NotificationPriority notificationPriority,
                        final NotificationCategory notificationCategory,
                        final List<Person> persons, final List<SspRole> sspRoles);

    /**
     * Creates new notification
     * @param subject
     * @param body
     * @param expirationDate
     * @param notificationPriority
     * @param notificationCategory
     * @param person
     * @return
     */
    Notification create(final String subject, final String body, final Date expirationDate,
                        final NotificationPriority notificationPriority,
                        final NotificationCategory notificationCategory,
                        final Person person);

    /**
     * Creates new notification
     * @param subject
     * @param body
     * @param expirationDate
     * @param notificationPriority
     * @param notificationCategory
     * @param sspRole
     * @return
     */
    Notification create(final String subject, final String body, final Date expirationDate,
                        final NotificationPriority notificationPriority,
                        final NotificationCategory notificationCategory,
                        final SspRole sspRole);

    /**
     * Acknowledge notification
     * @param notification
     */
    void read(Notification notification);

    void unread(Notification notification);
}
