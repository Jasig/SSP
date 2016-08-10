/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api.reference;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ExternalViewTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ExternalView;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ExternalViewService;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.ExternalViewTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@Controller
@RequestMapping("/1/reference/externalview")
@PreAuthorize(Permission.SECURITY_REFERENCE_EXTERNAL_VIEW_TOOL_WRITE)
public class ExternalViewController extends AbstractAuditableReferenceController<ExternalView, ExternalViewTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalViewController.class);

    @Autowired
    private transient SecurityService securityService;

    @Autowired
	protected transient ExternalViewService service;

	@Autowired
	protected transient ExternalViewTOFactory factory;

    @Autowired
    private transient PersonService personService;


    @Override
    protected AuditableCrudService<ExternalView> getService() {
        return service;
    }

	@Override
	protected TOFactory<ExternalViewTO, ExternalView> getFactory() {
		return factory;
	}

    @Override
	protected Logger getLogger() {
		return LOGGER;
	}


    protected ExternalViewController() {
        super(ExternalView.class, ExternalViewTO.class);
    }


	/**
	 * Persist a new instance of the specified object.
	 * <p>
	 * Must not include an id.
	 *
	 * @param obj
	 *            New instance to persist.
	 * @return Original instance plus the generated id.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If the specified data contains an id (since it shouldn't).
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ExternalViewTO create(@Valid @RequestBody final ExternalViewTO obj)
			throws ObjectNotFoundException,	ValidationException {
		return super.create(obj);
	}

	/**
	 * Persist any changes to the specified instance.
	 *
	 * @param id
	 *            Explicit id to the instance to persist.
	 * @param obj
	 *            Full instance to persist.
	 * @return The update data object instance.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If the specified id is null.
	 */
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody ExternalViewTO save(@PathVariable final UUID id, @Valid @RequestBody final ExternalViewTO obj)
			throws ValidationException, ObjectNotFoundException {
		return super.save(id, obj);
	}

	/**
	 * Marks the specified data instance with a status of
	 * {@link ObjectStatus#INACTIVE}.
	 *
	 * @param id
	 *            The id of the data instance to mark deleted.
	 * @return Success boolean.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id)
			throws ObjectNotFoundException {
		return super.delete(id);
	}

    @RequestMapping(value = "/{externalViewId}/{studentId}", method = RequestMethod.GET)
    public @ResponseBody ExternalViewTO getClientTimeout(@PathVariable final UUID externalViewId,
                                                         @PathVariable final UUID studentId) throws ObjectNotFoundException {

        final ExternalView model = service.get(externalViewId);
        if (model == null) {
            return null;
        }

        Person currentUser = null;
        Person selectedStudent = null;
        if (StringUtils.isNotBlank(model.getUrl())) {
            if (StringUtils.isNotBlank(model.getVariableUserIdentifier())) {
                currentUser = securityService.currentUser().getPerson();
            }

            if (studentId != null && StringUtils.isNotBlank(model.getVariableStudentIdentifier())) {
                selectedStudent = personService.get(studentId);
            }
        }

        return new ExternalViewTO(model, currentUser, selectedStudent);
    }
}