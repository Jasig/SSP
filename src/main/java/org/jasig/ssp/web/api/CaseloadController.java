package org.jasig.ssp.web.api;

import java.util.UUID;

import org.jasig.ssp.factory.CaseloadRecordTOFactory;
import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.CaseloadService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.CaseloadRecordTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PreAuthorize("hasRole('ROLE_PERSON_CASELOAD_READ')")
@RequestMapping("/1/person")
public class CaseloadController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CaseloadController.class);

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient CaseloadService service;

	@Autowired
	private transient ProgramStatusService programStatusService;

	@Autowired
	private transient CaseloadRecordTOFactory factory;

	@Autowired
	private transient SecurityService securityService;

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(value = "/caseload", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<CaseloadRecordTO> myCaseload(
			final @RequestParam(required = false) UUID programStatusId,
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

		final PagingWrapper<CaseloadRecord> caseload = service.caseLoadFor(
				programStatus, securityService.currentUser().getPerson(),
				SortingAndPaging.createForSingleSort(status, start, limit,
						sort, sortDirection, null));

		return new PagedResponse<CaseloadRecordTO>(true, caseload.getResults(),
				factory.asTOList(caseload.getRows()));
	}

	@RequestMapping(value = "/{personId}/caseload", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<CaseloadRecordTO> caseloadFor(
			@PathVariable final UUID personId,
			final @RequestParam(required = false) UUID programStatusId,
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

		final PagingWrapper<CaseloadRecord> caseload = service.caseLoadFor(
				programStatus, personService.get(personId),
				SortingAndPaging.createForSingleSort(status, start, limit,
						sort, sortDirection, null));

		return new PagedResponse<CaseloadRecordTO>(true, caseload.getResults(),
				factory.asTOList(caseload.getRows()));
	}
}
