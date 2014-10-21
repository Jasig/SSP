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
package org.jasig.ssp.service.reference;

import org.jasig.ssp.model.reference.SuccessIndicator;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.transferobject.reference.SuccessIndicatorTO;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface SuccessIndicatorService extends ReferenceService<SuccessIndicator> {

    /**
     * Same as {@link ReferenceService#create(org.jasig.ssp.model.Auditable)}
     * but accepts a TO representation of the entity to be created. This is
     * the preferred way to create a {@SuccessIndicator} and the other may
     * even be disabled in the implementation.
     *
     * @param obj
     * @return
     * @throws ValidationException
     */
    SuccessIndicator create(SuccessIndicatorTO obj) throws ValidationException;

    /**
     * Same as {@link ReferenceService#save(org.jasig.ssp.model.Auditable)}
     * but accepts a TO representation of the entity to be modified. This is
     * the preferred way to modify a {@SuccessIndicator} and the other may
     * even be disabled in the implementation.
     *
     * @param obj
     * @return
     * @throws ObjectNotFoundException
     * @throws ValidationException
     */
    SuccessIndicator save(SuccessIndicatorTO obj) throws ObjectNotFoundException, ValidationException;

}
