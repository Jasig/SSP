package org.jasig.ssp.model;

import java.util.Date;
import java.util.UUID;

public class AppointmentStartTime {
	
    private UUID personId;
    private Date startTime;
    
    public AppointmentStartTime() {
    }


    public AppointmentStartTime(UUID personId, Date startTime) {
        this.personId = personId;
        this.startTime = startTime;
    }

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
}