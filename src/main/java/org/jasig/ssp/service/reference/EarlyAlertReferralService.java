package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertReferral;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * EarlyAlertReferral service
 * 
 * @author jon.adams
 */
public interface EarlyAlertReferralService extends
		AuditableCrudService<EarlyAlertReferral> {

	@Override
	PagingWrapper<EarlyAlertReferral> getAll(SortingAndPaging sAndP);

	@Override
	EarlyAlertReferral get(UUID id) throws ObjectNotFoundException;

	@Override
	EarlyAlertReferral create(EarlyAlertReferral obj);

	@Override
	EarlyAlertReferral save(EarlyAlertReferral obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
