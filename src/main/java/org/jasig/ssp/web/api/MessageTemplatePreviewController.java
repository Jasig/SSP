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

import org.jasig.ssp.factory.reference.TemplateLiteTOFactory;
import org.jasig.ssp.factory.reference.TemplateTOFactory;
import org.jasig.ssp.model.*;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.*;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.*;
import org.jasig.ssp.transferobject.reference.MessageTemplateTO;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/1/messageTemplatePreview")
@PreAuthorize(Permission.SECURITY_REFERENCE_SYSTEM_CONFIG_WRITE)
public class MessageTemplatePreviewController extends AbstractBaseController {


	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageTemplatePreviewController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Autowired
	private MessageTemplateService messageTemplateService;

	/**
	 * Retrieves the specified list from persistent storage.
	 *
	 * @param id
	 *            The specific id to use to lookup the associated data.
	 * @return The specified instance if found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If that specified data is not invalid.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	MessageTemplateTO get(
			final @RequestParam(required = false) UUID id) throws ObjectNotFoundException,
			ValidationException {
		return messageTemplateService.createMessageTemplatePreview(id);
	}
}
