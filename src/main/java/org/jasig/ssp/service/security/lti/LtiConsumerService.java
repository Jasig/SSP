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
package org.jasig.ssp.service.security.lti;

import org.jasig.ssp.model.security.lti.LtiConsumer;
import org.jasig.ssp.model.security.lti.LtiLaunchRequest;
import org.jasig.ssp.model.security.lti.LtiLaunchResponse;
import org.jasig.ssp.security.exception.UserNotAuthorizedException;
import org.jasig.ssp.security.exception.UserNotEnabledException;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.PersonAttributesSearchException;
import org.jasig.ssp.transferobject.LtiConsumerTO;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface LtiConsumerService extends AuditableCrudService<LtiConsumer> {
	/**
	 * Preferred over {@link #create(org.jasig.ssp.model.Auditable)} since it
	 * ensures the service impl has the opportunity to implement all relevant
	 * validations.
	 *
	 * @param obj
	 * @return
	 */
	LtiConsumer create(LtiConsumerTO obj) throws ObjectNotFoundException, ValidationException;

	/**
	 * Preferred over {@link #save(org.jasig.ssp.model.Auditable)} since it
	 * ensures the service impl has the opportunoty to implement all relevant
	 * validations.
	 *
	 * @param obj
	 * @return
	 */
	LtiConsumer save(LtiConsumerTO obj) throws ObjectNotFoundException;

	/**
	 * Handle a request to launch SSP as a LTI tool.
	 *
	 * @param request
	 * @return
	 * @throws UserNotEnabledException Launching user did not resolve to an authenticable account
	 * @throws UserNotAuthorizedException Something is wrong with the current authorization context.
	 *   Usually indicates a system-level error with LTI OAuth handling, or a concurrent change
	 *   to the LTI tool consumer configuration in the SSP database
	 * @throws PersonAttributesSearchException System-level failure trying to resolve the launching
	 *   user to an authenticatable account
	 * @throws RuntimeException Not all errors are converted to the above types. Other system-level
	 *   failures may be represented by any number of unchecked exception types.
	 */
	LtiLaunchResponse processLaunch(LtiLaunchRequest request)
			throws UserNotEnabledException, UserNotAuthorizedException,
			PersonAttributesSearchException, RuntimeException;

}
