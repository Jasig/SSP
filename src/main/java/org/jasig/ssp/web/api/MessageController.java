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

import org.jasig.ssp.factory.MessageTOFactory;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.MessageTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Used for managing Messages sent or in the queue. Ideal
 *   actions are read, view details, re-send, alter-recipients,
 *   or delete messages not-sent/errors.
 */
@Controller
@RequestMapping("/1/message")
@PreAuthorize(Permission.SECURITY_REFERENCE_SYSTEM_CONFIG_WRITE)
public class MessageController extends AbstractBaseController {


	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Autowired
	private MessageService messageService;

    @Autowired
    private MessageTOFactory messageTOFactory;

	/**
	 * Retrieves the message list from persistent storage.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
    PagedResponse<MessageTO> get(
        final @RequestParam(required = false) ObjectStatus status,
        final @RequestParam(required = false) Integer start,
        final @RequestParam(required = false) Integer limit,
        final @RequestParam(required = false) String sort,
        final @RequestParam(required = false) String sortDirection) throws ObjectNotFoundException, ValidationException {

        // Run getAll
        final PagingWrapper<Message> messages = messageService.getMessages(
                SortingAndPaging.createForSingleSortWithPaging(
                        status == null ? ObjectStatus.ACTIVE : status, start,
                        limit, sort, sortDirection, "sentDate"));

        return new PagedResponse<MessageTO>(true, messages.getResults(), messageTOFactory.asTOList(messages.getRows()));
	}
}
