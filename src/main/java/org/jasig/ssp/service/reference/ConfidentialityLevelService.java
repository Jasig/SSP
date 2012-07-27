package org.jasig.ssp.service.reference;

import java.util.Collection;

import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.permissions.DataPermissions;
import org.jasig.ssp.service.ReferenceService;
import org.springframework.security.core.GrantedAuthority;

/**
 * ConfidentialityLevel service
 */
public interface ConfidentialityLevelService extends
		ReferenceService<ConfidentialityLevel> {

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

	Collection<ConfidentialityLevel> confidentialityLevelsForSspUser(
			SspUser user);
}