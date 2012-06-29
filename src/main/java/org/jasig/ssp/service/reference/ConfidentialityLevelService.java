package org.jasig.ssp.service.reference;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.permissions.DataPermissions;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.security.core.GrantedAuthority;

/**
 * ConfidentialityLevel service
 */
public interface ConfidentialityLevelService extends
		AuditableCrudService<ConfidentialityLevel> {

	@Override
	PagingWrapper<ConfidentialityLevel> getAll(SortingAndPaging sAndP);

	@Override
	ConfidentialityLevel get(UUID id) throws ObjectNotFoundException;

	@Override
	ConfidentialityLevel create(ConfidentialityLevel obj)
			throws ObjectNotFoundException, ValidationException;

	@Override
	ConfidentialityLevel save(ConfidentialityLevel obj)
			throws ObjectNotFoundException, ValidationException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	Collection<String> confidentialityLevelsAsPermissions();

	/**
	 * Gets all available {@link DataPermissions} as {@link GrantedAuthority}
	 * types.
	 * 
	 * @return All available {@link DataPermissions} as equivalent
	 *         {@link GrantedAuthority} types.
	 */
	Collection<GrantedAuthority> confidentialityLevelsAsGrantedAuthorities();

	Collection<ConfidentialityLevel> filterConfidentialityLevelsFromPermissions(
			Collection<String> permissions);

	Collection<ConfidentialityLevel> filterConfidentialityLevelsFromGrantedAuthorities(
			Collection<GrantedAuthority> authorities);
}