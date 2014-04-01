package org.jasig.ssp.transferobject.messagetemplate;

import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.EarlyAlertResponseTO;

public class EarlyAlertResponseMessageTemplateTO extends EarlyAlertResponseTO {

	private static final long serialVersionUID = 1L;
	private CoachPersonLiteTO creator;
	
	public EarlyAlertResponseMessageTemplateTO() {
	}


	public EarlyAlertResponseMessageTemplateTO(EarlyAlertResponse model, final Person creator) {
		super(model);
		this.creator = new CoachPersonLiteTO(creator);
	}

	public CoachPersonLiteTO getCreator() {
		return creator;
	}

	public void setCreator(CoachPersonLiteTO creator) {
		this.creator = creator;
	}

}
