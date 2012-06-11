package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

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

	/**
	 * Lazily load an instance for the specified identifier.
	 * 
	 * @param id
	 *            Identifier
	 * @return A lazily-loaded instance for the specified identifier.
	 */
	EarlyAlertReason load(UUID id);

	@Override
	EarlyAlertReason create(EarlyAlertReason obj)
			throws ObjectNotFoundException, ValidationException;

	@Override
	EarlyAlertReason save(EarlyAlertReason obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
