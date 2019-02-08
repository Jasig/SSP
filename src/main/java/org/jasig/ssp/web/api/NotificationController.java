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
package org.jasig.ssp.web.api;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.NotificationTOFactory;
import org.jasig.ssp.model.Notification;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.NotificationCategory;
import org.jasig.ssp.model.reference.NotificationPriority;
import org.jasig.ssp.model.reference.NotificationReadStatus;
import org.jasig.ssp.model.reference.SspRole;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.NotificationService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.NotificationTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Used for managing Notifications. Ideal
 *   actions are read, view details and delete/mark-read.
 */
@Controller
@RequestMapping("/1/notification")
@PreAuthorize(Permission.SECURITY_REFERENCE_SYSTEM_CONFIG_WRITE)
public class NotificationController extends AbstractBaseController {


	private static final Logger LOGGER = LoggerFactory
			.getLogger(NotificationController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private PersonService personService;

	@Autowired
    private NotificationTOFactory notificationTOFactory;

	/**
	 * Retrieves the notification list from persistent storage.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
    PagedResponse<NotificationTO> get(
        final @RequestParam(required = false) ObjectStatus status,
        final @RequestParam(required = false) Integer start,
        final @RequestParam(required = false) Integer limit,
        final @RequestParam(required = false) String sort,
        final @RequestParam(required = false) String sortDirection) throws ObjectNotFoundException, ValidationException {

		//TODO return all per person and/or user's role

        return null;
	}

	/**
	 * Retrieves the message list from persistent storage.
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<NotificationTO> getAll(
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sortProperty,
			final @RequestParam(required = false) String sortDirection) {

		// Run getAll
		final PagingWrapper<Notification> notifications = notificationService.getNotifications(
				SortingAndPaging.createForSingleSortWithPaging(
						ObjectStatus.ALL, start,
						limit, sortProperty, sortDirection, "createdDate"));

		return new PagedResponse<>(true, notifications.getResults(),
				notificationTOFactory.asTOList(notifications.getRows()));

	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<NotificationTO> search(
			final @RequestParam(required = false) UUID personId,
			final @RequestParam(required = false) String sspRole,
			final @RequestParam(required = false) NotificationReadStatus readStatus,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) throws ObjectNotFoundException {


		Person person = null;
		if (personId!=null) {
			person = personService.get(personId);
		}

		// Run getAll
		final PagingWrapper<Notification> notifications = notificationService.getNotifications(person, SspRole.valueOf(sspRole),
				readStatus,
				SortingAndPaging.createForSingleSortWithPaging(status, start, limit, sort, sortDirection, null));

		return new PagedResponse<>(true, notifications.getResults(),
				notificationTOFactory.asTOList(notifications.getRows()));
	}

	@RequestMapping(value = "/{id}/read", method = RequestMethod.GET)
	public @ResponseBody NotificationTO markRead(final @PathVariable UUID id) throws ObjectNotFoundException {

		Notification notification = notificationService.getNotification(id);
		if (notification == null) {
			throw new ObjectNotFoundException("Notification not found.", id.toString());
		}

		notificationService.read(notification);

		return notificationTOFactory.from(notification);
	}
	@RequestMapping(value = "/{id}/unread", method = RequestMethod.GET)
	public @ResponseBody NotificationTO markUnread(final @PathVariable UUID id) throws ObjectNotFoundException {

		Notification notification = notificationService.getNotification(id);
		if (notification == null) {
			throw new ObjectNotFoundException("Notification not found.", id.toString());
		}

		notificationService.unread(notification);

		return notificationTOFactory.from(notification);
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public @ResponseBody NotificationTO create(
			final @RequestParam(required = false) String subject,
			final @RequestParam(required = false) String body,
			final @RequestParam(required = false) NotificationPriority notificationPriority,
			final @RequestParam(required = false) NotificationCategory notificationCategory,
			final @RequestParam(required = false) List<SspRole> sspRoles) throws ObjectNotFoundException {
		Notification notification = notificationService.create(subject, body, new Date(), notificationPriority, notificationCategory, null, sspRoles);
		return notificationTOFactory.from(notification);
	}

}
