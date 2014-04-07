package org.jasig.ssp.transferobject.messagetemplate;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.PersonLiteTO;
import org.jasig.ssp.transferobject.TaskTO;

public class TaskMessageTemplateTO extends TaskTO {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7548769326538860374L;

	private CoachPersonLiteMessageTemplateTO person;
	
	private CoachPersonLiteMessageTemplateTO coach;
	
	private CoachPersonLiteMessageTemplateTO creator;
	
	public TaskMessageTemplateTO() {
	}

	public TaskMessageTemplateTO(Task task) {
		super(task);
		// TODO Auto-generated constructor stub
	}
	
	public TaskMessageTemplateTO(Task task, Person creator) {
		super(task);
		if(creator != null)
		this.creator = new CoachPersonLiteMessageTemplateTO(creator);
	}
	
	public CoachPersonLiteMessageTemplateTO getPerson() {
		return person;
	}

	public void setPerson(CoachPersonLiteMessageTemplateTO person) {
		this.person = person;
	}
	
	public CoachPersonLiteMessageTemplateTO getCoach() {
		return coach;
	}

	public void setCoach(CoachPersonLiteMessageTemplateTO coach) {
		this.coach = coach;
	}

	public CoachPersonLiteMessageTemplateTO getCreator() {
		return creator;
	}

	public void setCreator(CoachPersonLiteMessageTemplateTO creator) {
		this.creator = creator;
	}

	@Override
	public final void from(Task model){
		super.from(model);
		person = new CoachPersonLiteMessageTemplateTO(model.getPerson());
		if(model.getPerson().getCoach() != null){
			coach = new CoachPersonLiteMessageTemplateTO(model.getPerson().getCoach());
		}
	}

}
