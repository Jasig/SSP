package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * EarlyAlertReason service
 * 
 * @author jon.adams
 */
public interface EarlyAlertReasonService extends
		AuditableCrudService<EarlyAlertReason> {

	@Override
	PagingWrapper<EarlyAlertReason> getAll(SortingAndPaging sAndP);

	@Override
	EarlyAlertReason get(UUID id) throws ObjectNotFoundException;

	@Override
	EarlyAlertReason create(EarlyAlertReason obj);

	@Override
	EarlyAlertReason save(EarlyAlertReason obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
