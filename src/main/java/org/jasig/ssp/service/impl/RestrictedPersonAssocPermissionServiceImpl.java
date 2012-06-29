package org.jasig.ssp.service.impl;

import java.util.Collection;

import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.RestrictedPersonAssocPermissionService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestrictedPersonAssocPermissionServiceImpl implements
		RestrictedPersonAssocPermissionService {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RestrictedPersonAssocPermissionServiceImpl.class);

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Override
	public void checkPermissionForModelGivesExcpetion(
			final RestrictedPersonAssocAuditable model, final SspUser requestor) {

		if (!checkPermissionForModel(model, requestor)) {
			throw new AccessDeniedException("Access is denied for Model.");
		}
	}

	@Override
	public boolean checkPermissionForModel(
			final RestrictedPersonAssocAuditable model, final SspUser requestor) {

		final Collection<GrantedAuthority> permissions = requestor
				.getAuthorities();

		final Collection<ConfidentialityLevel> grantedLevels = confidentialityLevelService
				.filterConfidentialityLevelsFromGrantedAuthorities(permissions);

		if (grantedLevels.contains(model.getConfidentialityLevel())
				|| (model.getCreatedBy().equals(
						requestor.getPerson()))) {
			return true;
		}

		LOGGER.debug("Required ConfidentialityLevel not present: {}", model
				.getConfidentialityLevel().getName());
		return false;
	}
}