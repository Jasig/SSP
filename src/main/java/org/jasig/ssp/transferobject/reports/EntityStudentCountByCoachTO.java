package org.jasig.ssp.transferobject.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
	
}
