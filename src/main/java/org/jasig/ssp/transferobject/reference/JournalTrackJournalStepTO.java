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
import org.jasig.ssp.model.reference.JournalTrackJournalStep;
import org.jasig.ssp.transferobject.AbstractAuditableTO;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class JournalTrackJournalStepTO extends
		AbstractAuditableTO<JournalTrackJournalStep> implements
		TransferObject<JournalTrackJournalStep>, Serializable {

	private static final long serialVersionUID = 2080443166898776919L;

	private JournalTrackTO journalTrack;

	private JournalStepTO journalStep;

	public JournalTrackJournalStepTO() {
		super();
	}

	public JournalTrackJournalStepTO(
			final JournalTrackJournalStep model) {
		super();
		from(model);
	}

	@Override
	public final void from(final JournalTrackJournalStep model) {
		super.from(model);

		journalTrack = model.getJournalTrack() == null ? null
				: new JournalTrackTO(model.getJournalTrack());
		journalStep = model.getJournalStep() == null ? null
				: new JournalStepTO(model.getJournalStep());

	}

	public static List<JournalTrackJournalStepTO> toTOList(
			final Collection<JournalTrackJournalStep> models) {
		final List<JournalTrackJournalStepTO> tos = Lists.newArrayList();
		if ((models != null) && !models.isEmpty()) {
			for (JournalTrackJournalStep model : models) {
				tos.add(new JournalTrackJournalStepTO(model)); // NOPMD
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

	public JournalTrackTO getJournalTrack() {
		return journalTrack;
	}

	public void setJournalTrack(JournalTrackTO journalTrack) {
		this.journalTrack = journalTrack;
	}
}
