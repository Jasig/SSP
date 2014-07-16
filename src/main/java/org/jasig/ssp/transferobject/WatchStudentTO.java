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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.model.WatchStudent;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelLiteTO;

import com.google.common.collect.Lists;

/**
 * Goal transfer object
 */
public class WatchStudentTO
		extends AbstractAuditableTO<WatchStudent>
		implements TransferObject<WatchStudent>, Serializable {

	private static final long serialVersionUID = 5011875522731047877L;

	private UUID watcherId;
	
	private UUID studentId;

	private Date watchDate;
	
	public WatchStudentTO() {
		super();
	}

	@NotNull
	public WatchStudentTO(final WatchStudent model) {
		super();
		from(model);
	}

	@Override
	public final void from(final WatchStudent model) {
		super.from(model);
		this.setWatchDate(model.getWatchDate());
		//student or watcher should never be null.. If an NPE is thrown from this method, something else is wrong.
		this.setStudentId(model.getStudent().getId());
		this.setWatcherId(model.getPerson().getId());
	}

	/**
	 * Converts a list of models to equivalent transfer objects.
	 * 
	 * @param models
	 *            model tasks to convert to equivalent transfer objects
	 * @return List of equivalent transfer objects, or empty List if null or
	 *         empty.
	 */
	public static List<WatchStudentTO> toTOList(
			final Collection<WatchStudent> models) {
		final List<WatchStudentTO> tObjects = Lists.newArrayList();
		if (null != models) {
			for (final WatchStudent model : models) {
				tObjects.add(new WatchStudentTO(model)); // NOPMD
			}
		}
		return tObjects;
	}


	public Date getWatchDate() {
		return watchDate;
	}

	public void setWatchDate(Date watchDate) {
		this.watchDate = watchDate;
	}

	public UUID getWatcherId() {
		return watcherId;
	}

	public void setWatcherId(UUID watcherId) {
		this.watcherId = watcherId;
	}

	public UUID getStudentId() {
		return studentId;
	}

	public void setStudentId(UUID studentId) {
		this.studentId = studentId;
	}
}