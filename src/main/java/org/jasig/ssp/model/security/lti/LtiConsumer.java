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
package org.jasig.ssp.model.security.lti;

import org.jasig.ssp.model.Person;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue("lti-consumer")
public class LtiConsumer extends Person {

	private static final long serialVersionUID = -8951858310955188017L;

	@Column(nullable = true, length = 35)
	@Size(max = 35)
	private String ltiUserIdField;

	@Column(nullable = true, length = 35)
	@Size(max = 35)
	private String sspUserIdField;

	@Column(nullable = true, length = 35)
	@Size(max = 35)
	private String ltiSectionCodeField;

	@Column(nullable = true, length = 256)
	@Size(max = 256)
	private String secret;

	private static String FIRST_NAME = "Lti";

	public LtiConsumer() {
		super();
		super.setFirstName(FIRST_NAME);
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setConsumerKey(String consumerKey) {
		setUsername(consumerKey);
		setSchoolId(consumerKey);
	}

	public String getConsumerKey() {
		return getUsername();
	}

	public void setName(String name) {
		setLastName(name);
	}

	public String getName() {
		return getLastName();
	}

	public String getLtiUserIdField() {
		return ltiUserIdField;
	}

	public void setLtiUserIdField(String ltiUserIdField) {
		this.ltiUserIdField = ltiUserIdField;
	}

	public String getSspUserIdField() {
		return sspUserIdField;
	}

	public void setSspUserIdField(String sspUserIdField) {
		this.sspUserIdField = sspUserIdField;
	}

	public String getLtiSectionCodeField() {
		return ltiSectionCodeField;
	}

	public void setLtiSectionCodeField(String ltiSectionCodeField) {
		this.ltiSectionCodeField = ltiSectionCodeField;
	}
}
