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
package org.jasig.ssp.web.api.reference;

import java.util.ArrayList;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideQuestionTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.SelfHelpGuideQuestionService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideQuestionTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/selfHelpGuides/selfHelpGuideQuestions")
public class SelfHelpGuideQuestionController
		extends
		AbstractAuditableReferenceController<SelfHelpGuideQuestion, SelfHelpGuideQuestionTO> {

	@Autowired
	protected transient SelfHelpGuideQuestionService service;

	@Override
	protected AuditableCrudService<SelfHelpGuideQuestion> getService() {
		return service;
	}

	@Autowired
	protected transient SelfHelpGuideQuestionTOFactory factory;

	@Override
	protected TOFactory<SelfHelpGuideQuestionTO, SelfHelpGuideQuestion> getFactory() {
		return factory;
	}

	protected SelfHelpGuideQuestionController() {
		super(SelfHelpGuideQuestion.class, SelfHelpGuideQuestionTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SelfHelpGuideQuestionController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	@PreAuthorize(Permission.SECURITY_REFERENCE_WRITE)
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<SelfHelpGuideQuestionTO> getAllForParent(
			final @RequestParam(required = false) String selfReferenceGuideId
			) {
		if(selfReferenceGuideId == null || "".equals(selfReferenceGuideId))
		{
			return new PagedResponse<SelfHelpGuideQuestionTO>(true,0L,new ArrayList<SelfHelpGuideQuestionTO>());
		}

		final PagingWrapper<SelfHelpGuideQuestion> data =service.getAllForParent(
				SortingAndPaging.createForSingleSort(
						ObjectStatus.ACTIVE , null,
						null, null, null, "questionNumber"),selfReferenceGuideId);

		PagedResponse<SelfHelpGuideQuestionTO> pagedResponse = new PagedResponse<SelfHelpGuideQuestionTO>(true, data.getResults(), getFactory()
				.asTOList(data.getRows()));
		return pagedResponse;
	}
	
	@Override
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	@RequestMapping	
	public @ResponseBody
	PagedResponse<SelfHelpGuideQuestionTO> getAll(ObjectStatus status,
			Integer start, Integer limit, String sort, String sortDirection) {
		return super.getAll(status, start, limit, sort, sortDirection);
	}
}
