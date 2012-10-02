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

import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.SspUser;

/**
 * Service that has restricted access to model data based on a
 * {@link ConfidentialityLevel} set on the model.
 */
public interface RestrictedPersonAssocPermissionService {

	void checkPermissionForModelGivesExcpetion(
			final RestrictedPersonAssocAuditable model, final SspUser requestor);

	/**
	 * Checks that the requester has permissions to view the model data per its
	 * specified {@link ConfidentialityLevel}.
	 * 
	 * @param model
	 *            the model
	 * @param requester
	 *            the current user requesting access to the model data
	 * @return True if the requester has permission to view the model data.
	 */
	boolean checkPermissionForModel(final RestrictedPersonAssocAuditable model,
			final SspUser requester);
}