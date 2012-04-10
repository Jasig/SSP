package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.Referral;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

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
