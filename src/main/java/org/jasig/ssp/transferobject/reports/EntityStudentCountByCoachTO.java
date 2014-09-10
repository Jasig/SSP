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
package org.jasig.ssp.transferobject.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;

public class EntityStudentCountByCoachTO {
	
	public static Map<UUID,EntityStudentCountByCoachTO> getIndexedListByCoach(List<EntityStudentCountByCoachTO> entities){
		Map<UUID, EntityStudentCountByCoachTO> indexedList = new HashMap<UUID, EntityStudentCountByCoachTO>();
		for(EntityStudentCountByCoachTO entity : entities){
			indexedList.put(entity.getCoach().getId(), entity);
		}
		return indexedList;
	}
	
	
	private long studentCount;
	private long entityCount;
	private AuditPerson coach;
	
	public EntityStudentCountByCoachTO(){
		
	}

	public long getStudentCount() {
		return studentCount;
	}
	public void setStudentCount(long studentCount) {
		this.studentCount = studentCount;
	}
	public long getEntityCount() {
		return entityCount;
	}
	public void setEntityCount(long entityCount) {
		this.entityCount = entityCount;
	}
	public AuditPerson getCoach() {
		return coach;
	}
	public void setCoach(AuditPerson coach) {
		this.coach = coach;
	}

	/**
	 * Logical setter on top of the {@code coach} field. I.e. if that field has not yet been set and the given ID
	 * is not {@code null}, this will set it to a new {@link AuditPerson} and assign the given ID to it. Or if that
	 * field has already been set, the given ID will just be written through to it. No other fields on {@code coach}
	 * are modified, even if the given ID is {@code null}.
	 *
	 * <p>This and the other {@code setCoach*()} methods were introduced specifically for scenarios where the underlying
	 * persistent entity is a {@link Person} rather than an {@link AuditPerson}. The other design option in that case
	 * was to just read the {@link Person} out of the db and pass it to a {@code setCoachAsPerson()} that would
	 * consolidate all the work that is currently spread out over several {@code setCoach*()} methods. But the specific
	 * queries that needed to use this mechanism were using Hibernate projections grouped by entities ({@link Person}
	 * in this case) rather than simpler entity properties, which leads to N+1 query patterns. See
	 * <a href="http://stackoverflow.com/questions/4330480/prevent-hibernate-n1-selects-when-grouping-by-an-entity">this SO thread</a>.
	 * The workaround was to group by each of the fields that correspond an {@link AuditPerson}'s internals. Thus the
	 * need for the fine-grained logical setters in this class.</p>
	 *
	 * <p>Since this is a logical setter and thus might not behave quite as you expect were you to invoke a corresponding
	 * getter, and to avoid any possible accidents with JSON serialization, we've elected to not define getters for these
	 * logical setters. That said, are fairly weak justifications, though, so if a need arises in the future, it's
	 * probably OK to go ahead and add them.</p>
	 *
	 * @param id
	 */
	public void setCoachId(UUID id) {
		if ( this.coach == null ) {
			if ( id == null ) {
				return;
			}
			this.coach = new AuditPerson(id);
		} else {
			this.coach.setId(id);
		}
	}

	/**
	 * See {@link #setCoachId(java.util.UUID)}
	 *
	 * @param firstName
	 */
	public void setCoachFirstName(String firstName) {
		if ( this.coach == null ) {
			if ( firstName == null ) {
				return;
			}
			this.coach = new AuditPerson();
			this.coach.setFirstName(firstName);
		} else {
			this.coach.setFirstName(firstName);
		}
	}

	/**
	 * See {@link #setCoachId(java.util.UUID)}.
	 *
	 * @param lastName
	 */
	public void setCoachLastName(String lastName) {
		if ( this.coach == null ) {
			if ( lastName == null ) {
				return;
			}
			this.coach = new AuditPerson();
			this.coach.setLastName(lastName);
		} else {
			this.coach.setLastName(lastName);
		}
	}
	
}
