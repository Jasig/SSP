/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api.reference;

import java.util.UUID;
import javax.validation.Valid;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ConfigTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.RequestTrustService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.reference.ConfigTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

/**
 * Some basic methods for manipulating Config reference data.
 * <p>
 * Mapped to URI path <code>/1/reference/config</code>
 */
@Controller
@RequestMapping("/1/reference/config")
public class ConfigController
		extends
		AbstractAuditableReferenceController<Config, ConfigTO> {

	/**
	 * Auto-wired service-layer instance.
	 */
	@Autowired
	protected transient ConfigService service;

	@Autowired
	protected transient SecurityService securityService;

	@Autowired
	protected transient RequestTrustService requestTrustService;

	/**
	 * Auto-wired transfer object factory.
	 */
	@Autowired
	protected transient ConfigTOFactory factory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigController.class);

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	protected ConfigController() {
		super(Config.class, ConfigTO.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected AuditableCrudService<Config> getService() {
		return service;
	}

	@Override
	protected TOFactory<ConfigTO, Config> getFactory() {
		return factory;
	}

	/**
	 * WARNING: Creating new Config entries is not supported and this controller
	 * action will throw an UnsupportedOperationException. Use liquibase scripts
	 * to insert new configuration entries instead.
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	ConfigTO create(@Valid @RequestBody final ConfigTO obj)
			throws ObjectNotFoundException,
			ValidationException {
		throw new UnsupportedOperationException();
	}

	@RequestMapping(method = RequestMethod.GET, params = "name")
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	ConfigTO getByName(@RequestParam String name)
			throws ObjectNotFoundException {
		final Config config =
				((ConfigService) getService()).getByName(name);
		return filterSensitiveValues(config == null ? null : this.getFactory().from(config));
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<ConfigTO> getAll(
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) {

		PagedResponse<ConfigTO> rsp = super.getAll(status, start, limit, sort, sortDirection);
		return filterSensitiveValues(rsp);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	ConfigTO get(final @PathVariable UUID id) throws ObjectNotFoundException,
			ValidationException {
		return filterSensitiveValues(super.get(id));
	}

	// Yes, this is absolutely a hack. should either be a flag on the config
	// itself or services owning sensitive config should register themselves
	// centrally as such. That this is at the controller level, though, is not
	// really the bad part of this... the controller layer owns permissions
	// enforcement except for confidentiality levels. And for the specific
	// configs we're dealing with here, filtering at the service layer wouldn't
	// make sense b/c the config *values* (trusted IPs) need to be readable
	// there, even if the current user can't change those values.
	private PagedResponse<ConfigTO> filterSensitiveValues(PagedResponse<ConfigTO> rsp) {
		if ( securityService.hasAuthority(Permission.REFERENCE_WRITE) ) {
			return rsp;
		}

		for ( ConfigTO configTO : rsp.getRows() ) {
			filterSensitiveValues(configTO);
		}

		return rsp;
	}

	private ConfigTO filterSensitiveValues(ConfigTO configTO) {
		if ( !(securityService.hasAuthority(Permission.REFERENCE_WRITE)) ) {
			requestTrustService.obfuscateSensitiveConfig(configTO);
		}
		return configTO;
	}

}