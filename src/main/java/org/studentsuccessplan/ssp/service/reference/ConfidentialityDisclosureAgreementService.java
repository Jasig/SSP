package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.ConfidentialityDisclosureAgreement;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface ConfidentialityDisclosureAgreementService extends AuditableCrudService<ConfidentialityDisclosureAgreement> {

	@Override
	public List<ConfidentialityDisclosureAgreement> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public ConfidentialityDisclosureAgreement get(UUID id) throws ObjectNotFoundException;

	@Override
	public ConfidentialityDisclosureAgreement create(ConfidentialityDisclosureAgreement obj);

	@Override
	public ConfidentialityDisclosureAgreement save(ConfidentialityDisclosureAgreement obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
