package org.jasig.ssp.transferobject.reports;

import java.io.Serializable;
import java.util.ArrayList;

import org.jasig.ssp.model.Task;


/**
 * SpecialServicesReportingTO transfer object
 */
public class StudentActionPlanTO 
		implements Serializable {

	private static final long serialVersionUID = -6470796322082662019L;
	final ArrayList<Task> tasks;
	final String challengeName;
	final String challengeDescription;
	
	public StudentActionPlanTO(ArrayList<Task> tasks, String challengeName,
			String challengeDescription) {
		super();
		this.tasks = tasks;
		this.challengeName = challengeName;
		this.challengeDescription = challengeDescription;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public String getChallengeName() {
		return challengeName;
	}

	public String getChallengeDescription() {
		return challengeDescription;
	}

}
