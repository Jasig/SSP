package org.jasig.ssp.web.api;

import java.util.UUID;

import org.jasig.ssp.factory.PersonSearchResultTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.PersonSearchResult;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.PersonSearchResultTO;
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
@PreAuthorize("hasRole('ROLE_PERSON_SEARCH_READ')")
@RequestMapping("/1/person")
public class PersonSearchController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonSearchController.class);

	@Autowired
	private transient PersonSearchService service;

	@Autowired
	private transient ProgramStatusService programStatusService;

	@Autowired
	private transient PersonSearchResultTOFactory factory;

	@Autowired
	private transient SecurityService securityService;

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<PersonSearchResultTO> search(
			final @RequestParam String searchTerm,
			final @RequestParam(required = false) UUID programStatusId,
			final @RequestParam(required = false) Boolean outsideCaseload,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		ProgramStatus programStatus = null;
		if (null != programStatusId) {
			programStatus = programStatusService.get(programStatusId);
		}

		final PagingWrapper<PersonSearchResult> results = service.searchBy(
				programStatus, outsideCaseload, searchTerm,
				securityService.currentUser().getPerson(),
				SortingAndPaging.createForSingleSort(status, start, limit,
						sort, sortDirection, null));

		return new PagedResponse<PersonSearchResultTO>(true,
				results.getResults(), factory.asTOList(results.getRows()));
	}
}
