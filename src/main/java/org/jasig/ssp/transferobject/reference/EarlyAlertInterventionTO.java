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
 * 06/16/2014 - Jonathan Hart IRSC TAPS 20140039 - Created EarlyAlertInterventionTO.java
 */
package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.reference.EarlyAlertIntervention;
import org.jasig.ssp.transferobject.NamedTO;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Sets;

/**
 * EarlyAlertIntervention reference transfer objects
 * 
 * Based on EarlyAlertSuggestion by @author jon.adams
 */
public class EarlyAlertInterventionTO extends
		AbstractReferenceTO<EarlyAlertIntervention>
		implements TransferObject<EarlyAlertIntervention>, NamedTO, Serializable {

	private static final long serialVersionUID = 3743316755135265679L;

	private short sortOrder; // NOPMD by jon.adams on 5/4/12 11:16

	/**
	 * Empty constructor
	 */
	public EarlyAlertInterventionTO() {
		super();
	}

	/**
	 * Constructor to initialize the required properties of the class.
	 * 
	 * @param id
	 *            identifier
	 * @param name
	 *            name shown to the user
	 */
	public EarlyAlertInterventionTO(@NotNull final UUID id,
			@NotNull final String name) {
		super();
		super.setId(id);
		super.setName(name);
	}

	/**
	 * Constructor to initialize all properties of the class.
	 * 
	 * @param id
	 *            identifier
	 * @param name
	 *            name shown to the user
	 * @param description
	 *            description shown to the user in detail views
	 * @param sortOrder
	 *            default sort order for use when displaying a list of these
	 *            reference objects to the user
	 */
	public EarlyAlertInterventionTO(@NotNull final UUID id,
			@NotNull final String name,
			final String description, final short sortOrder) { // NOPMD by jon
		super(id, name, description);
		this.sortOrder = sortOrder;
	}

	/**
	 * Construct a transfer object based on the data in the supplied model.
	 * 
	 * @param model
	 *            Model data to copy
	 */
	public EarlyAlertInterventionTO(@NotNull final EarlyAlertIntervention model) {
		super();

		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		from(model);
	}

	@Override
	public final void from(final EarlyAlertIntervention model) {
		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		super.from(model);
		sortOrder = model.getSortOrder();
	}

	/**
	 * @return the sortOrder
	 */
	public short getSortOrder() { // NOPMD by jon on 5/4/12 11:16
		return sortOrder;
	}

	/**
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(final short sortOrder) { // NOPMD by jon on 5/4/12
		if (sortOrder < 0) {
			throw new IllegalArgumentException(
					"Sort order must be 0 or a positive integer, not "
							+ sortOrder);
		}

		this.sortOrder = sortOrder;
	}

	/**
	 * Convert a collection of models to a collection of equivalent transfer
	 * objects.
	 * 
	 * @param models
	 *            Collection of models to copy
	 * @return A collection of equivalent transfer objects.
	 */
	public static Set<EarlyAlertInterventionTO> toTOSet(
			@NotNull final Collection<EarlyAlertIntervention> models) {
		final Set<EarlyAlertInterventionTO> tObjects = Sets.newHashSet();
		for (final EarlyAlertIntervention model : models) {
			tObjects.add(new EarlyAlertInterventionTO(model)); // NOPMD by jon
		}

		return tObjects;
	}
}