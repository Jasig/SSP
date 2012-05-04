package org.jasig.ssp.security;


import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public final class SimplePermissionService implements PermissionService {
	
	public static final String USER_PERMISSIONS_KEY = "org.jasig.ssp.security.SimplePermissionService.PERMISSION_SET_KEY";

	@Override
	public void initializePermissionsIfNecessary(PortletRequest req) {
		UserPermissions perms = (UserPermissions) req.getPortletSession(true).getAttribute(USER_PERMISSIONS_KEY);
		if (perms == null) {
			perms = UserPermissions.createUserPermissions(req);
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
			perms = UserPermissions.createUserPermissions(req);
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
	
}
