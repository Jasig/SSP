package org.jasig.ssp.web.api.external;

import org.jasig.ssp.factory.external.ExternalTOFactory;
import org.jasig.ssp.factory.external.TermTOFactory;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.external.ExternalDataService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.transferobject.external.TermTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/term")
public class TermController extends AbstractExternalController<TermTO, Term> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TermController.class);

	@Autowired
	protected transient TermService service;

	@Override
	protected ExternalDataService<Term> getService() {
		return service;
	}

	@Autowired
	protected transient TermTOFactory factory;

	@Override
	protected ExternalTOFactory<TermTO, Term> getFactory() {
		return factory;
	}

	protected TermController() {
		super(TermTO.class, Term.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}