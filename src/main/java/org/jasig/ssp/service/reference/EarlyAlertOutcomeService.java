package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * EarlyAlertOutcome service
 * 
 * @author jon.adams
 */
public interface EarlyAlertOutcomeService extends
		AuditableCrudService<EarlyAlertOutcome> {

	@Override
	PagingWrapper<EarlyAlertOutcome> getAll(SortingAndPaging sAndP);

	@Override
	EarlyAlertOutcome get(UUID id) throws ObjectNotFoundException;

	@Override
	EarlyAlertOutcome create(EarlyAlertOutcome obj);

	@Override
	EarlyAlertOutcome save(EarlyAlertOutcome obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
