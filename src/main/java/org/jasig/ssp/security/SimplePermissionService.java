package org.jasig.ssp.security;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class SimplePermissionService implements PermissionService {
	
	private static final String USER_PERMISSIONS_KEY = "org.jasig.ssp.security.SimplePermissionService.PERMISSION_SET_KEY";

	@Override
	public void initializePermissionsIfNecessary(PortletRequest req) {
		UserPermissions perms = (UserPermissions) req.getPortletSession(true).getAttribute(USER_PERMISSIONS_KEY);
		if (perms == null) {
			perms = createUserPermissions(req);
			req.getPortletSession(true).setAttribute(USER_PERMISSIONS_KEY, perms, PortletSession.APPLICATION_SCOPE);
		}
	}

	/*
	 * Public API
	 */

	@Override
	public boolean checkPermission(SspPermission p, PortletRequest req) {
		UserPermissions perms = (UserPermissions) req.getPortletSession(true).getAttribute(USER_PERMISSIONS_KEY);
		if (perms == null) {
			// Should never happen because there's always a PortletRequest before a HttpServletRequest
			String msg = "The UserPermissions object has not been initialized";
			throw new IllegalStateException(msg);
		} else if (perms.isExpired()) {
			// Time for a refresh...
			perms = createUserPermissions(req);
			req.getPortletSession(true).setAttribute(USER_PERMISSIONS_KEY, perms, PortletSession.APPLICATION_SCOPE);
		}
		return perms.contains(p);
	}

	@Override
	public boolean checkPermission(SspPermission p, HttpServletRequest req) {
		UserPermissions perms = (UserPermissions) req.getSession(true).getAttribute(USER_PERMISSIONS_KEY);
		if (perms == null) {
			// Should never happen because there's always a PortletRequest before a HttpServletRequest
			String msg = "The UserPermissions object has not been initialized";
			throw new IllegalStateException(msg);
		}
		return perms.contains(p);
	}

	/*
	 * Implementation
	 */
	
	/**
	 * Build the UserPermissions object for the user.  We need the Portlet API 
	 * to do it, so it can only be created on the portlet side.  Thankfully 
	 * there will always be a PortletRequest before a ServletRequest.
	 * 
	 * @param req
	 * @return
	 */
	private UserPermissions createUserPermissions(final PortletRequest req) {
		
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
	
	private static final class UserPermissions {
		
		private static final long TIME_TO_LIME = 1000L * 60L * 5L;  // 5 minutes
		
		private final Set<SspPermission> entries;
		private final long expireTime;
		
		public UserPermissions(Set<SspPermission> entries) {
			this.entries = Collections.unmodifiableSet(entries);
			this.expireTime = System.currentTimeMillis() + TIME_TO_LIME;
		}
		
		public boolean contains(SspPermission p) {
			return entries.contains(p);
		}
		
		public boolean isExpired() {
			return System.currentTimeMillis() > expireTime;
		}
		
	}
	
}
