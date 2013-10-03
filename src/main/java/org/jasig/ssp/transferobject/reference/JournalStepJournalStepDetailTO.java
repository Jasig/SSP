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

import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;
import org.jasig.ssp.transferobject.AbstractAuditableTO;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class JournalStepJournalStepDetailTO extends
		AbstractAuditableTO<JournalStepJournalStepDetail> implements
		TransferObject<JournalStepJournalStepDetail>, Serializable {

	private static final long serialVersionUID = 2080443166898776919L;

	private JournalStepTO journalStep;

	private JournalStepDetailTO journalStepDetail;
	
	private Integer sortOrder;

	public JournalStepJournalStepDetailTO() {
		super();
	}

	public JournalStepJournalStepDetailTO(
			final JournalStepJournalStepDetail model) {
		super();
		from(model);
	}

	@Override
	public final void from(final JournalStepJournalStepDetail model) {
		super.from(model);

		journalStep = model.getJournalStep() == null ? null
				: new JournalStepTO(model.getJournalStep());
		journalStepDetail = model.getJournalStepDetail() == null ? null
				: new JournalStepDetailTO(model.getJournalStepDetail());
		sortOrder = model.getSortOrder();
	}

	public static List<JournalStepJournalStepDetailTO> toTOList(
			final Collection<JournalStepJournalStepDetail> models) {
		final List<JournalStepJournalStepDetailTO> tos = Lists.newArrayList();
		if ((models != null) && !models.isEmpty()) {
			for (JournalStepJournalStepDetail model : models) {
				tos.add(new JournalStepJournalStepDetailTO(model)); // NOPMD
			}
		}

		return tos;
	}

	public JournalStepTO getJournalStep() {
		return journalStep;
	}

	public void setJournalStep(final JournalStepTO journalStep) {
		this.journalStep = journalStep;
	}

	public JournalStepDetailTO getJournalStepDetail() {
		return journalStepDetail;
	}

	public void setJournalStepDetail(final JournalStepDetailTO journalStepDetail) {
		this.journalStepDetail = journalStepDetail;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
}
