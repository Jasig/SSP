package org.studentsuccessplan.ssp.service.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface ConfidentialityDisclosureAgreementService extends
		AuditableCrudService<ConfidentialityDisclosureAgreement> {

	@Override
	public PagingWrapper<ConfidentialityDisclosureAgreement> getAll(
			SortingAndPaging sAndP);

	@Override
	public ConfidentialityDisclosureAgreement get(UUID id)
			throws ObjectNotFoundException;

	@Override
	public ConfidentialityDisclosureAgreement create(
			ConfidentialityDisclosureAgreement obj);

	@Override
	public ConfidentialityDisclosureAgreement save(
			ConfidentialityDisclosureAgreement obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
