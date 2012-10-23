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
package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Appointment;

import com.google.common.collect.Lists;

public class AppointmentTO
		extends AbstractAuditableTO<Appointment>
		implements TransferObject<Appointment>, Serializable {

	private static final long serialVersionUID = 6691057299444847287L;

	private UUID personId;

	private Date startTime, endTime;

	private boolean attended;

	public AppointmentTO() {
		super();
	}

	public AppointmentTO(final Appointment model) {
		super();
		from(model);
	}

	@Override
	public final void from(final Appointment model) {
		super.from(model);

		personId = (model.getPerson() == null) ? null : model.getPerson()
				.getId();
		startTime = model.getStartTime();
		endTime = model.getEndTime();
		attended = model.isAttended();
	}

	/**
	 * Converts a list of models to equivalent transfer objects.
	 * 
	 * @param models
	 *            model tasks to convert to equivalent transfer objects
	 * @return List of equivalent transfer objects, or empty List if null or
	 *         empty.
	 */
	public static List<AppointmentTO> toTOList(
			final Collection<Appointment> models) {
		final List<AppointmentTO> tObjects = Lists.newArrayList();
		if (null != models) {
			for (final Appointment model : models) {
				tObjects.add(new AppointmentTO(model));
			}
		}

		return tObjects;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public Date getStartTime() {
		return (startTime == null) ? null : new Date(startTime.getTime());
	}

	public void setStartTime(final Date startTime) {
		this.startTime = (startTime == null) ? null : new Date(
				startTime.getTime());
	}

	public Date getEndTime() {
		return (endTime == null) ? null : new Date(endTime.getTime());
	}

	public void setEndTime(final Date endTime) {
		this.endTime = (endTime == null) ? null : new Date(endTime.getTime());
	}

	public boolean isAttended() {
		return attended;
	}

	public void setAttended(final boolean attended) {
		this.attended = attended;
	}

}
