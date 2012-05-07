package org.jasig.ssp.web.api.reference;

import java.util.UUID;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.JournalStepTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.service.reference.JournalStepService;
import org.jasig.ssp.service.reference.JournalTrackService;
import org.jasig.ssp.transferobject.PagingTO;
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

	@Override
	protected JournalStepService getService() {
		return service;
	}

	@Autowired
	private transient JournalTrackService journalTrackService;

	@Autowired
	protected transient JournalStepTOFactory factory;

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

	@RequestMapping(value = "/{id}/journalSteps/", method = RequestMethod.GET)
	public @ResponseBody
	PagingTO<JournalStepTO, JournalStep> getAllForJournalTrack(
			final @PathVariable UUID journalTrackId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws Exception {

		final JournalTrack journalTrack = journalTrackService
				.get(journalTrackId);

		final PagingWrapper<JournalStep> data = getService()
				.getAllForJournalTrack(journalTrack,
						SortingAndPaging.createForSingleSort(status, start,
								limit, sort, sortDirection, "sortOrder"));

		return new PagingTO<JournalStepTO, JournalStep>(true,
				data.getResults(), getFactory()
						.asTOList(data.getRows()));
	}
}
