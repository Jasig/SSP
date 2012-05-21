package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface ConfidentialityDisclosureAgreementService extends
		AuditableCrudService<ConfidentialityDisclosureAgreement> {

	@Override
	PagingWrapper<ConfidentialityDisclosureAgreement> getAll(
			SortingAndPaging sAndP);

	@Override
	ConfidentialityDisclosureAgreement get(UUID id)
			throws ObjectNotFoundException;

	@Override
	ConfidentialityDisclosureAgreement create(
			ConfidentialityDisclosureAgreement obj)
			throws ObjectNotFoundException, ValidationException;

	@Override
	ConfidentialityDisclosureAgreement save(
			ConfidentialityDisclosureAgreement obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
