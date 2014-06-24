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
 * 06/12/2014 - Jonathan Hart IRSC TAPS 20140039 - Created EarlyAlertInterventionTOFactory.java transfer object factory for converting back and forth from EarlyAlertIntervention models
 */
package org.jasig.ssp.factory.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.reference.EarlyAlertIntervention;
import org.jasig.ssp.transferobject.reference.EarlyAlertInterventionTO;

/**
 * EarlyAlertIntervention transfer object factory for converting back and forth
 * from EarlyAlertIntervention models.
 * 
 * Based upon EarlyAlertSuggestion By @author jon.adams
 * 
 */
public interface EarlyAlertInterventionTOFactory extends
		TOFactory<EarlyAlertInterventionTO, EarlyAlertIntervention> {
}
