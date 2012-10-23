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

import org.jasig.ssp.model.reference.ProgramStatusChangeReason;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

/**
 * ProgramStatusChangeReason transfer object
 * 
 * @author jon.adams
 * 
 */
public class ProgramStatusChangeReasonTO
		extends AbstractReferenceTO<ProgramStatusChangeReason>
		implements TransferObject<ProgramStatusChangeReason> { // NOPMD
	/**
	 * Empty constructor
	 */
	public ProgramStatusChangeReasonTO() {
		super();
	}

	public ProgramStatusChangeReasonTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ProgramStatusChangeReasonTO(final ProgramStatusChangeReason model) {
		super();
		from(model);
	}

	public static List<ProgramStatusChangeReasonTO> toTOList(
			final Collection<ProgramStatusChangeReason> models) {
		final List<ProgramStatusChangeReasonTO> tObjects = Lists.newArrayList();
		for (final ProgramStatusChangeReason model : models) {
			tObjects.add(new ProgramStatusChangeReasonTO(model)); // NOPMD
		}

		return tObjects;
	}
}