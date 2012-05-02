package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

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
