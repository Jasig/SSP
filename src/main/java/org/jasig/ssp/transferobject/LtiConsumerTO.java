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
package org.jasig.ssp.transferobject;

import java.util.UUID;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.jasig.ssp.model.security.lti.LtiConsumer;

public class LtiConsumerTO extends AbstractAuditableTO<LtiConsumer>
	implements TransferObject<LtiConsumer> {

	private UUID id;

	private String secret;

	private String name;

	private String consumerKey;

	private String ltiUserIdField;

	private String sspUserIdField;

	private String ltiSectionCodeField;

	private boolean secretChange;

	public LtiConsumerTO() {
		super();
	}

	public LtiConsumerTO(final LtiConsumer model) {
		super();
		from(model);
	}

	@Override
	public void from(final LtiConsumer model) {
		super.from(model);
		secret = model.getSecret();
		consumerKey = model.getConsumerKey();
		ltiUserIdField = model.getLtiUserIdField();
		sspUserIdField = model.getSspUserIdField();
		ltiSectionCodeField = model.getLtiSectionCodeField();
		setName(model.getName());
		id = model.getId();
	}
	

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	// This field isn't mapped out of the model, but still, don't ever
	// broadcast secrets in API responses
	@JsonIgnore
	public String getSecret() {
		return secret;
	}

	/**
	 * Use this to set a secret change, including a {@link null} to delete the
	 * existing secret. But because we do support that sort of deletion, you
	 * also need to set {@link secretChange} otherwise whatever is set here
	 * will be ignored.
	 *
	 * @param secret
	 */
	@JsonProperty
	public void setSecret(String secret) {
		this.secret = secret;
	}

	
	// Only relevant on write operations
	@JsonIgnore
	public boolean isSecretChange() {
		return secretChange;
	}

	// Only relevant on write operations
	@JsonProperty
	public void setSecretChange(boolean secretChange) {
		this.secretChange = secretChange;
	}
	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
