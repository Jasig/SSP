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
package org.jasig.ssp.service;


import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.SuccessIndicator;
import org.jasig.ssp.transferobject.EvaluatedSuccessIndicatorTO;
import java.util.List;
import java.util.UUID;


public interface EvaluatedSuccessIndicatorService {

    /**
     * Evaluates Success Indicators for given person.
     * @param personId the person UUID
     * @param status the person status
     * @param indicators  if null all indicators will be evaluated for person
     * @return the list of evaluated success indicator transfer objects
     * @throws ObjectNotFoundException if the data does not exist
     */
    List<EvaluatedSuccessIndicatorTO> getForPerson(UUID personId, ObjectStatus status,
                                     List<SuccessIndicator> indicators) throws ObjectNotFoundException;
}
