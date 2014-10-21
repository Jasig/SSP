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
package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.reference.SuccessIndicatorDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.SuccessIndicatorTOFactory;
import org.jasig.ssp.model.reference.SuccessIndicator;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.SuccessIndicatorTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SuccessIndicatorTOFactoryImpl extends AbstractReferenceTOFactory<SuccessIndicatorTO, SuccessIndicator>
        implements SuccessIndicatorTOFactory {

    public SuccessIndicatorTOFactoryImpl() {
        super(SuccessIndicatorTO.class, SuccessIndicator.class);
    }

    /**
     * Overriden to throw {@code UnsupportedOperationException}. See
     * <a href="this page">https://wiki.jasig.org/display/SSP/SSP+Technical+Design+Proposal%3A+Application+Layering</a>.
     * Encapsulate all db access via {@link org.jasig.ssp.service.reference.SuccessIndicatorService}.
     *
     * @return
     */
    @Override
    @Deprecated
    protected SuccessIndicatorDao getDao() {
        throw new UnsupportedOperationException("Should not be using a DAO from this factory implementation. " +
                "Encapsulate all db operations via SuccessIndicatorService");
    }

    /**
     * Overriden to throw {@code UnsupportedOperationException}. See
     * <a href="this page">https://wiki.jasig.org/display/SSP/SSP+Technical+Design+Proposal%3A+Application+Layering</a>.
     * Use {@link org.jasig.ssp.service.reference.SuccessIndicatorService#save(SuccessIndicatorTO} or
     * {@link org.jasig.ssp.service.reference.SuccessIndicatorService#create(SuccessIndicatorTO)} instead
     *
     * @param tObject
     *            Transfer object to copy
     * @return
     * @throws ObjectNotFoundException
     */
    @Override
    @Deprecated
    public SuccessIndicator from(final SuccessIndicatorTO tObject) throws ObjectNotFoundException {
        throw new UnsupportedOperationException("Not allowed to edit SuccessIndicator entities by rote. Use " +
                "SuccessIndicatorService.create() or SuccessIndicatorService.save()");
    }

    /**
     * Overriden to throw {@code UnsupportedOperationException}. See
     * <a href="this page">https://wiki.jasig.org/display/SSP/SSP+Technical+Design+Proposal%3A+Application+Layering</a>.
     * Use {@link org.jasig.ssp.service.reference.SuccessIndicatorService#get(java.util.UUID)} instead.
     *
     * @param id
     *            Load model for this identifier
     *
     * @return
     * @throws ObjectNotFoundException
     */
    @Override
    @Deprecated
    public SuccessIndicator from(final UUID id) throws ObjectNotFoundException {
        throw new UnsupportedOperationException("Not allowed to access the db from this factory implementation. Use " +
                "SuccessIndicatorService.get()");
    }
}
