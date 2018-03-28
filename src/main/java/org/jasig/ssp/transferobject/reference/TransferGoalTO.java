/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import org.jasig.ssp.model.reference.TransferGoal;
import org.jasig.ssp.transferobject.TransferObject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


/**
 *Tag transfer object
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferGoalTO
		extends AbstractReferenceTO<TransferGoal>
		implements TransferObject<TransferGoal> {

	private String code;

	/**
	 * Empty constructor
	 */
	public TransferGoalTO() {
		super();
	}

	public TransferGoalTO(final TransferGoal model) {
		super();
		from(model);
	}

	public TransferGoalTO(final UUID id, final String name,
                          final String description) {
		super(id, name, description);
	}

	

	@Override
	public final void from(final TransferGoal model) {
		/*if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}*/

		super.from(model);
	}

	

	public static List<TransferGoalTO> toTOList(
			final Collection<TransferGoal> models) {
		final List<TransferGoalTO> tObjects = Lists.newArrayList();
		for (final TransferGoal model : models) {
			tObjects.add(new TransferGoalTO(model));
		}

		return tObjects;
	}
}