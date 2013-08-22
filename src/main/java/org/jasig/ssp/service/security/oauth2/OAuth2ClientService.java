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
package org.jasig.ssp.service.security.oauth2;


import org.jasig.ssp.model.security.oauth2.OAuth2Client;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.OAuth2ClientTO;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface OAuth2ClientService extends AuditableCrudService<OAuth2Client> {
	/**
	 * Preferred over {@link #create(org.jasig.ssp.model.Auditable)} since it
	 * ensures the service impl has the opportunity to implement all relevant
	 * validations.
	 *
	 * @param obj
	 * @return
	 */
	OAuth2Client create(OAuth2ClientTO obj) throws ObjectNotFoundException, ValidationException;

	/**
	 * Preferred over {@link #save(org.jasig.ssp.model.Auditable)} since it
	 * ensures the service impl has the opportunoty to implement all relevant
	 * validations.
	 *
	 * @param obj
	 * @return
	 */
	OAuth2Client save(OAuth2ClientTO obj) throws ObjectNotFoundException;
}
