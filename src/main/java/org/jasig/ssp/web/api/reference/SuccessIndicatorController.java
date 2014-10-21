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

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.SuccessIndicatorTOFactory;
import org.jasig.ssp.model.reference.SuccessIndicator;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.SuccessIndicatorService;
import org.jasig.ssp.transferobject.reference.SuccessIndicatorTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/1/reference/successIndicator")
public class SuccessIndicatorController extends
        AbstractAuditableReferenceController<SuccessIndicator, SuccessIndicatorTO> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SuccessIndicatorController.class);

    @Autowired
    private transient SuccessIndicatorService service;

    @Autowired
    private transient SuccessIndicatorTOFactory factory;


    protected SuccessIndicatorController() {
        super(SuccessIndicator.class, SuccessIndicatorTO.class);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    SuccessIndicatorTO create(@Valid @RequestBody final SuccessIndicatorTO obj) throws ObjectNotFoundException,
            ValidationException {
        final SuccessIndicator model = ((SuccessIndicatorService) getService()).create(obj);
        if ( model == null ) {
            return null;
        }
        return this.instantiateTO(model);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    SuccessIndicatorTO save(@PathVariable final UUID id, @Valid @RequestBody final SuccessIndicatorTO obj)
            throws ValidationException, ObjectNotFoundException {

        if (obj.getId() == null) {
            obj.setId(id);
        }

        final SuccessIndicator model = ((SuccessIndicatorService) getService()).save(obj);
        if ( model == null ) {
            return null;
        }
        return this.instantiateTO(model);
    }

    @Override
    protected String getDefaultSortColumn() {
        return "sortOrder";
    }

    @Override
    protected AuditableCrudService<SuccessIndicator> getService() {
        return service;
    }

    @Override
    protected TOFactory<SuccessIndicatorTO, SuccessIndicator> getFactory() {
        return factory;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
