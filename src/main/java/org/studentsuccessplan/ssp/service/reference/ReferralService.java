package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Referral;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface ReferralService extends AuditableCrudService<Referral> {

	@Override
	List<Referral> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	Referral get(UUID id) throws ObjectNotFoundException; // NOPMD by jon on 4/7/12 9:41 PM

	@Override
	Referral create(Referral obj);

	@Override
	Referral save(Referral obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
