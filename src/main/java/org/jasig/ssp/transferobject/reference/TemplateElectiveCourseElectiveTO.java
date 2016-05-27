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

import com.google.common.collect.Lists;
import org.jasig.ssp.model.TemplateElectiveCourseElective;
import org.jasig.ssp.transferobject.AbstractMapElectiveCourseTO;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class TemplateElectiveCourseElectiveTO extends AbstractMapElectiveCourseTO<TemplateElectiveCourseElective> {

	private static final long serialVersionUID = 7775312780775745728L;

	private UUID mapTemplateElectiveCourseId;

	private int sortOrder;

	public TemplateElectiveCourseElectiveTO() {
		super();
	}

	public TemplateElectiveCourseElectiveTO(final TemplateElectiveCourseElective model) {
		super();
		from(model);
	}

	public static List<TemplateElectiveCourseElectiveTO> toTOList(
			final Collection<TemplateElectiveCourseElective> models) {
		final List<TemplateElectiveCourseElectiveTO> tObjects = Lists.newArrayList();
		for (TemplateElectiveCourseElective model : models) {
			tObjects.add(new TemplateElectiveCourseElectiveTO(model)); // NOPMD by jon.adams
		}

		return tObjects;
	}

	public final void from(final TemplateElectiveCourseElective model) {
		super.from(model);
		setMapTemplateElectiveCourseId(model.getTemplateElectiveCourse().getId());
//		sortOrder = model.getSortOrder();
	}

	public UUID getMapTemplateElectiveCourseId() {
		return mapTemplateElectiveCourseId;
	}

	public void setMapTemplateElectiveCourseId(UUID mapTemplateElectiveCourseId) {
		this.mapTemplateElectiveCourseId = mapTemplateElectiveCourseId;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(final int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getName() {return getFormattedCourse();}

	public void setName(String name) {
		// Do nothing
	}
}
