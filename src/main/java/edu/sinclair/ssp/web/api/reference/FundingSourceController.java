package edu.sinclair.ssp.web.api.reference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sinclair.ssp.model.reference.FundingSource;
import edu.sinclair.ssp.service.reference.FundingSourceService;
import edu.sinclair.ssp.transferobject.reference.FundingSourceTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/fundingSource")
public class FundingSourceController extends
		AbstractAuditableReferenceController<FundingSource, FundingSourceTO> {

	@Autowired
	protected FundingSourceController(FundingSourceService service) {
		super(service, FundingSource.class, FundingSourceTO.class);
	}
}
