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

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.Race;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class RaceTO
		extends AbstractReferenceTO<Race>
		implements TransferObject<Race> { // NOPMD by jon.adams
	
	private String code;

	public RaceTO() {
		super();
	}

	public RaceTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public RaceTO(final Race model) {
		super();
		from(model);
	}
	
	@Override
	public final void from(final Race model) {
		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		super.from(model);		
		code = model.getCode();		
	}
	
	/**
	 * @return the race code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @param code external ref code
	 * 				the race code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	public static List<RaceTO> toTOList(
			final Collection<Race> models) {
		final List<RaceTO> tObjects = Lists.newArrayList();
		for (final Race model : models) {
			tObjects.add(new RaceTO(model)); // NOPMD by jon.adams
		}

		return tObjects;
	}
}