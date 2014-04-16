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
	private CoachPersonLiteTO coach;
	
	public EntityStudentCountByCoachTO(){
		
	}
	public EntityStudentCountByCoachTO(long studentCount, long journalCount,
			Person coach) {
		super();
		this.studentCount = studentCount;
		this.entityCount = journalCount;
		this.coach = new CoachPersonLiteTO(coach);
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
	public CoachPersonLiteTO getCoach() {
		return coach;
	}
	public void setCoach(Person coach) {
		this.coach =  new CoachPersonLiteTO(coach);
	}
	
	public void setCoach(AuditPerson coach) {
		this.coach =  new CoachPersonLiteTO(coach);
	}
	
	public void setCoach(UUID coach) {
		this.coach =  new CoachPersonLiteTO();
		this.coach.setId(coach);
	}
	
}
