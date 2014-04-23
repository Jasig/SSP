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
package org.jasig.ssp.transferobject.messagetemplate;

import java.util.List;

import org.jasig.ssp.factory.reference.EarlyAlertOutreachTOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertReferralTOFactory;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.EarlyAlertResponseTO;
import org.jasig.ssp.transferobject.reference.EarlyAlertOutcomeTO;
import org.jasig.ssp.transferobject.reference.EarlyAlertOutreachTO;
import org.jasig.ssp.transferobject.reference.EarlyAlertReferralTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class EarlyAlertResponseMessageTemplateTO extends EarlyAlertResponseTO {

	private static final long serialVersionUID = 1L;
	private CoachPersonLiteMessageTemplateTO creator;
	private EarlyAlertOutcomeTO earlyAlertOutcome;
	private List<EarlyAlertReferralTO> earlyAlertReferrals;
	private List<EarlyAlertOutreachTO> earlyAlertOutreach;

	public EarlyAlertResponseMessageTemplateTO() {
	}


	public EarlyAlertResponseMessageTemplateTO(EarlyAlertResponse model, final Person creator) {
		super(model);
		this.setEarlyAlertOutcome(new EarlyAlertOutcomeTO(model.getEarlyAlertOutcome()));
		this.creator = new CoachPersonLiteMessageTemplateTO(creator);
		this.setEarlyAlertOutcomeOtherDescription(model.getEarlyAlertOutcomeOtherDescription());
		
	}

	public CoachPersonLiteMessageTemplateTO getCreator() {
		return creator;
	}

	public void setCreator(CoachPersonLiteMessageTemplateTO creator) {
		this.creator = creator;
	}


	public EarlyAlertOutcomeTO getEarlyAlertOutcome() {
		return earlyAlertOutcome;
	}


	public void setEarlyAlertOutcome(EarlyAlertOutcomeTO earlyAlertOutcome) {
		this.earlyAlertOutcome = earlyAlertOutcome;
	}


	public List<EarlyAlertReferralTO> getEarlyAlertReferrals() {
		return earlyAlertReferrals;
	}


	public void setEarlyAlertReferrals(
			List<EarlyAlertReferralTO> earlyAlertReferrals) {
		this.earlyAlertReferrals = earlyAlertReferrals;
	}


	public List<EarlyAlertOutreachTO> getEarlyAlertOutreach() {
		return earlyAlertOutreach;
	}


	public void setEarlyAlertOutreach(List<EarlyAlertOutreachTO> earlyAlertOutreach) {
		this.earlyAlertOutreach = earlyAlertOutreach;
	}
	
}
