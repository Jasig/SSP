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
package org.jasig.ssp.service;

import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.transferobject.PersonLiteTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Not an abstract base interface to mixed into any service for managing specific MAP entities (that's
 * {@link AbstractPlanService}), but a contract for a service managing all MAP "plans" polymorphically. I.e. for
 * querying across {@link Plan} and {@link Template}
 *
 */
public interface MapAbstractPlanService {

	PagingWrapper<AbstractPlan> getAll(SortingAndPaging sAndP);

	PagingWrapper<PersonLiteTO> getAllOwnersLite(SortingAndPaging sAndP);

	PagingWrapper<AbstractPlan> getAllForOwner(SortingAndPaging sAndP);

}
