package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * EarlyAlertOutreach service
 * 
 * @author jon.adams
 */
public interface EarlyAlertOutreachService extends
		AuditableCrudService<EarlyAlertOutreach> {

	@Override
	PagingWrapper<EarlyAlertOutreach> getAll(SortingAndPaging sAndP);

	@Override
	EarlyAlertOutreach get(UUID id) throws ObjectNotFoundException;

	@Override
	EarlyAlertOutreach create(EarlyAlertOutreach obj);

	@Override
	EarlyAlertOutreach save(EarlyAlertOutreach obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
