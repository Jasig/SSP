package org.jasig.ssp.security;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.portlet.PortletRequest;

final class UserPermissions {

	private static final long TIME_TO_LIVE = 1000L * 60L * 5L; // 5 minutes

	private final Set<SspPermission> entries;
	private final long expireTime;

	public UserPermissions(Set<SspPermission> entries) {
		this.entries = Collections.unmodifiableSet(entries);
		expireTime = System.currentTimeMillis() + TIME_TO_LIVE;
	}

	public boolean contains(SspPermission p) {
		return entries.contains(p);
	}

	/**
	 * Is permission expired?
	 * 
	 * @return True if the permission has expired.
	 */
	public boolean isExpired() {
		return System.currentTimeMillis() > expireTime;
	}

	/**
	 * Build the UserPermissions object for the user. We need the Portlet API to
	 * do it, so it can only be created on the portlet side. Thankfully there
	 * will always be a PortletRequest before a ServletRequest.
	 * 
	 * @param req
	 *            Portlet request
	 * @return The UserPermissions for the user.
	 */
	public static UserPermissions createUserPermissions(final PortletRequest req) {
		Set<SspPermission> entries = new HashSet<SspPermission>();

		for (AccessType y : AccessType.values()) {
			if (req.isUserInRole(y.getRoleName())) {
				entries.add(y);
			}
		}

		for (Role r : Role.values()) {
			if (req.isUserInRole(r.getRoleName())) {
				entries.add(r);
			}
		}

		return new UserPermissions(entries);
	}
}