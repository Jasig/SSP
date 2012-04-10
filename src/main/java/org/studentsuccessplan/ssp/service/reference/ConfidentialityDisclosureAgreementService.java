package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

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
