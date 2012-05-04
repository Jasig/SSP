package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * EarlyAlertSuggestion service
 * 
 * @author jon.adams
 */
public interface EarlyAlertSuggestionService extends
		AuditableCrudService<EarlyAlertSuggestion> {

	@Override
	PagingWrapper<EarlyAlertSuggestion> getAll(SortingAndPaging sAndP);

	@Override
	EarlyAlertSuggestion get(UUID id) throws ObjectNotFoundException;

	@Override
	EarlyAlertSuggestion create(EarlyAlertSuggestion obj);

	@Override
	EarlyAlertSuggestion save(EarlyAlertSuggestion obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
