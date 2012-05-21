package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

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

	/**
	 * Loads the specified instance from persistent storage.
	 * 
	 * @param id
	 *            identifier to load
	 * @return The specified instance
	 */
	EarlyAlertOutreach load(UUID id);

	@Override
	EarlyAlertOutreach create(EarlyAlertOutreach obj)
			throws ObjectNotFoundException, ValidationException;

	@Override
	EarlyAlertOutreach save(EarlyAlertOutreach obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}