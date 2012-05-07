package org.jasig.ssp.web.api.reference;

import java.util.UUID;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.JournalStepDetailTOFactory;
import org.jasig.ssp.factory.reference.JournalStepTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.service.reference.JournalStepDetailService;
import org.jasig.ssp.service.reference.JournalStepService;
import org.jasig.ssp.transferobject.PagingTO;
import org.jasig.ssp.transferobject.reference.JournalStepDetailTO;
import org.jasig.ssp.transferobject.reference.JournalStepTO;
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

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/journalStep")
public class JournalStepController
		extends
		AbstractAuditableReferenceController<JournalStep, JournalStepTO> {

	@Autowired
	protected transient JournalStepService service;

	@Autowired
	protected transient JournalStepDetailService journalStepDetailService;

	@Override
	protected JournalStepService getService() {
		return service;
	}

	@Autowired
	protected transient JournalStepTOFactory factory;

	@Autowired
	protected transient JournalStepDetailTOFactory journalStepDetailTOFactory;

	@Override
	protected TOFactory<JournalStepTO, JournalStep> getFactory() {
		return factory;
	}

	protected JournalStepController() {
		super(JournalStep.class, JournalStepTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalStepController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(value = "/{id}/journalStepDetails/", method = RequestMethod.GET)
	public @ResponseBody
	PagingTO<JournalStepDetailTO, JournalStepDetail> getAllForJournalStep(
			final @PathVariable UUID journalStepId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws Exception {

		final JournalStep journalStep = getService()
				.get(journalStepId);

		final PagingWrapper<JournalStepDetail> data = journalStepDetailService
				.getAllForJournalStep(journalStep,
						SortingAndPaging.createForSingleSort(status, start,
								limit, sort, sortDirection, "sortOrder"));

		return new PagingTO<JournalStepDetailTO, JournalStepDetail>(true,
				data.getResults(), journalStepDetailTOFactory
						.asTOList(data.getRows()));
	}
}
