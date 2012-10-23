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
package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ConfidentialityDisclosureAgreementTO extends
		AbstractReferenceTO<ConfidentialityDisclosureAgreement>
		implements TransferObject<ConfidentialityDisclosureAgreement> {

	/**
	 * text of the agreement
	 * 
	 * Optional, null allowed, max length 64000 characters.
	 */
	private String text;

	/**
	 * Empty constructor
	 */
	public ConfidentialityDisclosureAgreementTO() {
		super();
	}

	public ConfidentialityDisclosureAgreementTO(final UUID id,
			@NotNull final String name, final String description,
			@NotNull final String text) {
		super(id, name, description);
		this.text = text;
	}

	public ConfidentialityDisclosureAgreementTO(
			final ConfidentialityDisclosureAgreement model) {
		super();
		from(model);
	}

	@Override
	public final void from(final ConfidentialityDisclosureAgreement model) {
		super.from(model);
		text = model.getText();
	}

	public String getText() {
		return text;
	}

	/**
	 * Sets the text of the agreement
	 * 
	 * Optional, but null not allowed, max length 64000 characters.
	 * 
	 * @param text
	 *            text of the agreement
	 */
	public void setText(final String text) {
		this.text = text;
	}

	public static List<ConfidentialityDisclosureAgreementTO> toTOList(
			final Collection<ConfidentialityDisclosureAgreement> models) {
		final List<ConfidentialityDisclosureAgreementTO> tObjects = Lists
				.newArrayList();
		for (final ConfidentialityDisclosureAgreement model : models) {
			tObjects.add(new ConfidentialityDisclosureAgreementTO(model)); // NOPMD
		}

		return tObjects;
	}
}