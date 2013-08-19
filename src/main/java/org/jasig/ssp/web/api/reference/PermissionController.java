package org.jasig.ssp.web.api.reference;

import java.util.List;

import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.PermissionTO;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/reference/permission")
public class PermissionController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PermissionController.class);

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<PermissionTO> getAll() {
		List<PermissionTO> perms =
				Permission.PERMISSION_TRANSFER_OBJECTS;
		return new PagedResponse<PermissionTO>(true,
				new Long(perms.size()), perms);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
