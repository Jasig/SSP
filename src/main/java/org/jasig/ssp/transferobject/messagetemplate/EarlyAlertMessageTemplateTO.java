package org.jasig.ssp.transferobject.messagetemplate;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.PersonLiteTO;

public class EarlyAlertMessageTemplateTO extends EarlyAlertTO {


	private static final long serialVersionUID = 1432740352046594692L;
	
	private PersonLiteTO person;
	
	private CoachPersonLiteTO coach;
	
	private CoachPersonLiteTO creator;
	
	public EarlyAlertMessageTemplateTO(EarlyAlert earlyAlert, Person creator) {
		super(earlyAlert);
		if(creator != null)
			this.creator = new CoachPersonLiteTO(creator);
	}

	public EarlyAlertMessageTemplateTO(EarlyAlert earlyAlert) {
		super(earlyAlert);
	}
	
	public PersonLiteTO getPerson() {
		return person;
	}

	public void setPerson(PersonLiteTO person) {
		this.person = person;
	}
	
	public CoachPersonLiteTO getCoach() {
		return coach;
	}

	public void setCoach(CoachPersonLiteTO coach) {
		this.coach = coach;
	}

	public CoachPersonLiteTO getCreator() {
		return creator;
	}

	public void setCreator(CoachPersonLiteTO creator) {
		this.creator = creator;
	}

	@Override
	public final void from(EarlyAlert model){
		super.from(model);
		person = new PersonLiteTO(model.getPerson());
		if(model.getPerson().getCoach() != null){
			coach = new CoachPersonLiteTO(model.getPerson().getCoach());
		}
	}

}
