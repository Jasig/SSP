package org.jasig.ssp.transferobject.messagetemplate;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.PersonLiteTO;

public class EarlyAlertMessageTemplateTO extends EarlyAlertTO {


	private static final long serialVersionUID = 1432740352046594692L;
	
	private PersonLiteTO person;

	public EarlyAlertMessageTemplateTO(EarlyAlert earlyAlert) {
		super(earlyAlert);
		
	}
	
	public PersonLiteTO getPerson() {
		return person;
	}

	public void setPerson(PersonLiteTO person) {
		this.person = person;
	}

	@Override
	public final void from(EarlyAlert model){
		super.from(model);
		person = new PersonLiteTO(model.getPerson());
	}

}
