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
package org.jasig.ssp.model.reference;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jasig.ssp.transferobject.jsonserializer.CodeAndProperty;
import org.jasig.ssp.transferobject.jsonserializer.CodeAndPropertySerializer;

/**
 * Enumeration of the States in the USA
 */
@JsonSerialize(using = CodeAndPropertySerializer.class)
public enum States implements CodeAndProperty {

	AL("Alabama"), AK("Alaska"), AS("American Samoa"), AZ("Arizona"), AR(
			"Arkansas"), CA("California"), CO("Colorado"), CT("Connecticut"), DE(
			"Delaware"), DC("Dist. of Columbia"), FL("Florida"), GA("Georgia"), GU(
			"Guam"), HI("Hawaii"), ID("Idaho"), IL("Illinois"), IN("Indiana"), IA(
			"Iowa"), KS("Kansas"), KY("Kentucky"), LA("Louisiana"), ME("Maine"), MD(
			"Maryland"), MH("Marshall Islands"), MA("Massachusetts"), MI(
			"Michigan"), FM("Micronesia"), MN("Minnesota"), MS("Mississippi"), MO(
			"Missouri"), MT("Montana"), NE("Nebraska"), NV("Nevada"), NH(
			"New Hampshire"), NJ("New Jersey"), NM("New Mexico"), NY("New York"), NC(
			"North Carolina"), ND("North Dakota"), MP("Northern Marianas"), OH(
			"Ohio"), OK("Oklahoma"), OR("Oregon"), PW("Palau"), PA(
			"Pennsylvania"), PR("Puerto Rico"), RI("Rhode Island"), SC(
			"South Carolina"), SD("South Dakota"), TN("Tennessee"), TX("Texas"), UT(
			"Utah"), VT("Vermont"), VA("Virginia"), VI("Virgin Islands"), WA(
			"Washington"), WV("West Virginia"), WI("Wisconsin"), WY("Wyoming");

	private String title;

	private States(final String title) {
		this.title = title;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getCode() {
		return name();
	}
}