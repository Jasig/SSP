package org.jasig.ssp.security;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

public interface PermissionService {
	
	void initializePermissionsIfNecessary(PortletRequest req);

	boolean checkPermission(SspPermission permission, PortletRequest req);

	boolean checkPermission(SspPermission permission, HttpServletRequest req);

}
