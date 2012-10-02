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

import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class JournalStepDetailTO extends AbstractReferenceTO<JournalStepDetail>
		implements TransferObject<JournalStepDetail>, Serializable {

	private static final long serialVersionUID = 7539604345170318617L;

	private int sortOrder;

	public JournalStepDetailTO() {
		super();
	}

	public JournalStepDetailTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public JournalStepDetailTO(final JournalStepDetail model) {
		super();
		from(model);
	}

	public static List<JournalStepDetailTO> toTOList(
			final Collection<JournalStepDetail> models) {
		final List<JournalStepDetailTO> tObjects = Lists.newArrayList();
		for (JournalStepDetail model : models) {
			tObjects.add(new JournalStepDetailTO(model)); // NOPMD by jon.adams
		}

		return tObjects;
	}

	@Override
	public final void from(final JournalStepDetail model) {
		super.from(model);
		sortOrder = model.getSortOrder();
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(final int sortOrder) {
		this.sortOrder = sortOrder;
	}
}
