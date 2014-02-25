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

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ChallengeReferralSearchResultTOFactory;
import org.jasig.ssp.factory.reference.ChallengeReferralTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.model.reference.ChallengeReferralSearchResult;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.PersonSearchResult2TO;
import org.jasig.ssp.transferobject.PersonSearchResultTO;
import org.jasig.ssp.transferobject.reference.ChallengeReferralSearchFormTO;
import org.jasig.ssp.transferobject.reference.ChallengeReferralSearchResultTO;
import org.jasig.ssp.transferobject.reference.ChallengeReferralTO;
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

/**
 * ChallengeReferral controller
 */
@Controller
@RequestMapping("/1/reference/challengeReferral")
public class ChallengeReferralController
		extends
		AbstractAuditableReferenceController<ChallengeReferral, ChallengeReferralTO> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeReferralController.class);

	@Autowired
	protected transient ChallengeReferralService service;

	@Override
	protected AuditableCrudService<ChallengeReferral> getService() {
		return service;
	}

	@Autowired
	protected transient ChallengeReferralTOFactory factory;
	
	@Autowired
	protected transient ChallengeReferralSearchResultTOFactory searchResultFactory;
	
	@Override
	protected TOFactory<ChallengeReferralTO, ChallengeReferral> getFactory() {
		return factory;
	}

	protected ChallengeReferralController() {
		super(ChallengeReferral.class, ChallengeReferralTO.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody PagedResponse<ChallengeReferralSearchResultTO> search(final @RequestParam(required = false) UUID categoryId,
			final @RequestParam(required = false) UUID challengeId,
			final @RequestParam(required = false) String searchPhrase,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection,
			final HttpServletRequest request
			){
		
		PagingWrapper<ChallengeReferralSearchResult> results = service.summarySearch(new ChallengeReferralSearchFormTO(categoryId, 
				challengeId, 
				searchPhrase,
				SortingAndPaging.createForSingleSortWithPaging(status, start, limit,
						sort, sortDirection, null)));
		return new PagedResponse<ChallengeReferralSearchResultTO>(true,
				results.getResults(), searchResultFactory.asTOList(results.getRows()));	
	}

}
