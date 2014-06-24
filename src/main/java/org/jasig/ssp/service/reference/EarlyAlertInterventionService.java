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
 
 /*
 * IRSC CUSTOMIZATIONS
 * 06/16/2014 - Jonathan Hart IRSC TAPS 20140039 - Created EarlyAlertInterventionService.java
 */
package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertIntervention;
import org.jasig.ssp.service.ReferenceService;

/**
 * EarlyAlertIntervention service
 * 
 * Based on EarlyAlertSuggestion service by @author jon.adams
 */
public interface EarlyAlertInterventionService extends
		ReferenceService<EarlyAlertIntervention> {

	/**
	 * Lazily load an instance for the specified identifier.
	 * 
	 * @param id
	 *            Identifier
	 * @return A lazily-loaded instance for the specified identifier.
	 */
	EarlyAlertIntervention load(UUID id);

}
