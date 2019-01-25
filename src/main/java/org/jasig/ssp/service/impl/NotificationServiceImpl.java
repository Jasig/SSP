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
import org.jasig.ssp.model.*;
import org.jasig.ssp.model.reference.NotificationCategory;
import org.jasig.ssp.model.reference.NotificationPriority;
import org.jasig.ssp.model.reference.NotificationReadStatus;
import org.jasig.ssp.model.reference.SspRole;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.NotificationService;
import org.jasig.ssp.service.ObjectNotFoundException;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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

	@Override
	public Notification getNotification(UUID id) throws ObjectNotFoundException {
		return notificationDao.get(id);
	}

	@Override
	@Transactional(readOnly = true)
	public PagingWrapper<Notification> getNotifications(final SortingAndPaging sortingAndPaging) {
		return notificationDao.getAll(ObjectStatus.ALL);
	}

	@Override
	@Transactional(readOnly = true)
	public PagingWrapper<Notification> getNotifications(final SspRole sspRole,
                                                        final NotificationReadStatus notificationReadStatus,
														final SortingAndPaging sortingAndPaging) {
		return getNotifications(null, sspRole, notificationReadStatus, sortingAndPaging);
	}

	@Override
	@Transactional(readOnly = true)
	public PagingWrapper<Notification> getNotifications(final Person person,
                                                        final NotificationReadStatus notificationReadStatus,
														final SortingAndPaging sortingAndPaging) {
		return getNotifications(person, null, notificationReadStatus, sortingAndPaging);
	}

	@Override
	@Transactional(readOnly = true)
	public PagingWrapper<Notification> getNotifications(final Person person, final SspRole sspRole,
														final NotificationReadStatus notificationReadStatus,
														final SortingAndPaging sortingAndPaging) {
		if (person == null && sspRole == null) {
			throw new IllegalArgumentException("Either Person or SSPRole required for getting Notifications");
		}
		UUID personId = null;
		if (person!=null) {
			personId = person.getId();
		}
		return notificationDao.getNotifications(personId, sspRole,
                notificationReadStatus==null?NotificationReadStatus.ALL:notificationReadStatus,
                sortingAndPaging);
	}

	@Override
	@Transactional
	public Notification create(final String subject, final String body, final Date expirationDate,
						final NotificationPriority notificationPriority, final NotificationCategory notificationCategory,
						final Person person) {
		List<Person> persons = Arrays.asList(person);
		return create(subject, body, expirationDate, notificationPriority, notificationCategory, persons, null);
	}
	@Override
	@Transactional
	public Notification create(final String subject, final String body, final Date expirationDate,
						final NotificationPriority notificationPriority, final NotificationCategory notificationCategory,
						final SspRole sspRole) {
		List<SspRole> sspRoles = Arrays.asList(sspRole);
		return create(subject, body, expirationDate, notificationPriority, notificationCategory, null, sspRoles);
	}
	@Override
	@Transactional
	public Notification create(final String subject, final String body, Date expirationDate,
						final NotificationPriority notificationPriority, final NotificationCategory notificationCategory,
						final List<Person> persons, final List<SspRole> sspRoles) {
		if (subject == null) {
			throw new IllegalArgumentException("Subject missing.");
		}
		if (body == null) {
			throw new IllegalArgumentException("Body missing.");
		}
		if (notificationPriority == null) {
			throw new IllegalArgumentException("Notification Priority missing.");
		}
		if (notificationCategory == null) {
			throw new IllegalArgumentException("Notification Category missing.");
		}

		if ((persons == null || persons.size()==0) && (sspRoles == null || sspRoles.size()==0))  {
			throw new IllegalArgumentException("Person to notify and/or SSP Role must be set to create notification.");
		}
		if (expirationDate==null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date date = null;
			try {
				expirationDate = sdf.parse("2199/12/31");
			} catch (ParseException e) {
				expirationDate = new Date();
			}
		}

		Notification notification = new Notification(subject, body, expirationDate, 0,
				notificationPriority, notificationCategory);

		Set<NotificationRecipient> recipients = new HashSet<>();
		if (persons != null && persons.size() > 0) {
			for (Person person : persons) {
				NotificationRecipient recipient = new NotificationRecipient(person, notification);
				recipients.add(recipient);
			}
		}
		if (sspRoles != null && sspRoles.size() > 0) {
			for (SspRole sspRole : sspRoles) {
				NotificationRecipient recipient = new NotificationRecipient(sspRole, notification);
				recipients.add(recipient);
			}
		}
		notification.setNotificationRecipients(recipients);

		notification = checkForDuplicate(notification);

		return notificationDao.save(notification);
	}

	private Notification checkForDuplicate(Notification newNotification) {
        Notification dupNotification = notificationDao.getNotification(newNotification);
        if (dupNotification==null) {
            return newNotification;
        }

        dupNotification.setDuplicateCount(dupNotification.getDuplicateCount() + 1);

        if (newNotification.getNotificationRecipients()!=null) {
            for (NotificationRecipient newRecipient : newNotification.getNotificationRecipients()) {
                boolean found = false;
                if (dupNotification.getNotificationRecipients()!=null) {
                    for (NotificationRecipient dupRecipient : dupNotification.getNotificationRecipients()) {
                        if ((newRecipient.getPerson() == null && dupRecipient.getPerson() == null
                                && newRecipient.getSspRole() != null && dupRecipient.getSspRole() != null
                                && newRecipient.getSspRole().equals(dupRecipient.getSspRole())) ||

                                (newRecipient.getSspRole() == null && dupRecipient.getSspRole() == null
                                        && newRecipient.getPerson() != null && dupRecipient.getPerson() != null
                                        && newRecipient.getPerson().getId().equals(dupRecipient.getPerson().getId())) ||

                                (newRecipient.getSspRole() != null && dupRecipient.getSspRole() != null
                                        && newRecipient.getSspRole().equals(dupRecipient.getSspRole())
                                        && newRecipient.getPerson() != null && dupRecipient.getPerson() != null
                                        && newRecipient.getPerson().getId().equals(dupRecipient.getPerson().getId()))) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    newRecipient.setNotification(dupNotification);
                    dupNotification.getNotificationRecipients().add(newRecipient);
                }
            }
        }
        return dupNotification;
    }

	@Override
    @Transactional
    public void read(final Notification notification) {
        upsertNotificationReadStatus(notification, NotificationReadStatus.READ);
	    notificationDao.save(notification);
    }

	@Override
	@Transactional
	public void unread(final Notification notification) {
	    upsertNotificationReadStatus(notification, NotificationReadStatus.UNREAD);
		notificationDao.save(notification);
	}

	/*
	 * Marking a notification as read sets the ObjectStatus indicator to INACTIVE.
	 * Marking a notification as unread sets the ObjectStatus indicator to ACTIVE.
	 */
	private void upsertNotificationReadStatus(Notification notification,
											  NotificationReadStatus notificationReadStatus) {
        final SspUser sspUser = securityService.currentUser();
        NotificationRead notificationRead = getNotificationRead(notification, sspUser);

        if (notificationRead==null) {
            notificationRead = new NotificationRead(sspUser.getPerson(), notification);
            notification.getNotificationReads().add(notificationRead);
        }

        notificationRead.setObjectStatus(
                    notificationReadStatus==NotificationReadStatus.READ?ObjectStatus.INACTIVE:ObjectStatus.ACTIVE);

    }
	private NotificationRead getNotificationRead(Notification notification, SspUser sspUser) {
		Set<NotificationRead> notificationReadList = notification.getNotificationReads();

		if (notificationReadList!=null) {
			for (NotificationRead notificationRead : notificationReadList) {
				if (notificationRead.getPerson().getId() == sspUser.getPerson().getId()) {
					return notificationRead;
				}
			}
		}
		return null;
	}
}
