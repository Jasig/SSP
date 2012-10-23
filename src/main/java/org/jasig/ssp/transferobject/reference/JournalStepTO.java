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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

/**
 * JournalStep transfer object
 */
public class JournalStepTO extends AbstractReferenceTO<JournalStep>
		implements TransferObject<JournalStep>, Serializable {

	private static final long serialVersionUID = 918093582949321351L;

	private int sortOrder;

	private boolean usedForTransition = false;

	public JournalStepTO() {
		super();
	}

	public JournalStepTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public JournalStepTO(final JournalStep model) {
		super();
		from(model);
	}

	public static List<JournalStepTO> toTOList(
			final Collection<JournalStep> models) {
		final List<JournalStepTO> tObjects = Lists.newArrayList();
		for (final JournalStep model : models) {
			tObjects.add(new JournalStepTO(model)); // NOPMD
		}
		return tObjects;
	}

	@Override
	public final void from(final JournalStep model) {
		super.from(model);
		sortOrder = model.getSortOrder();
		usedForTransition = model.isUsedForTransition();
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(final int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the usedForTransition
	 */
	public boolean isUsedForTransition() {
		return usedForTransition;
	}

	/**
	 * @param usedForTransition
	 *            the usedForTransition to set
	 */
	public void setUsedForTransition(final boolean usedForTransition) {
		this.usedForTransition = usedForTransition;
	}
}