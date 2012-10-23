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
package org.jasig.ssp.factory.impl;

import java.util.HashSet;
import java.util.UUID;

import org.jasig.ssp.dao.EarlyAlertResponseDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.EarlyAlertResponseTOFactory;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.model.reference.EarlyAlertReferral;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.EarlyAlertOutreachService;
import org.jasig.ssp.service.reference.EarlyAlertReferralService;
import org.jasig.ssp.transferobject.EarlyAlertResponseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertResponse transfer object factory
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional(readOnly = true)
public class EarlyAlertResponseTOFactoryImpl extends
		AbstractAuditableTOFactory<EarlyAlertResponseTO, EarlyAlertResponse>
		implements EarlyAlertResponseTOFactory {

	/**
	 * Construct an EarlyAlertResponse transfer object factory with the specific
	 * class types for use by the super class methods.
	 */
	public EarlyAlertResponseTOFactoryImpl() {
		super(EarlyAlertResponseTO.class, EarlyAlertResponse.class);
	}

	@Autowired
	private transient EarlyAlertResponseDao dao;

	@Autowired
	private transient EarlyAlertService earlyAlertService;

	@Autowired
	private transient EarlyAlertOutcomeService earlyAlertOutcomeService;

	@Autowired
	private transient EarlyAlertOutreachService earlyAlertOutreachService;

	@Autowired
	private transient EarlyAlertReferralService earlyAlertReferralService;

	@Override
	protected EarlyAlertResponseDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertResponse from(final EarlyAlertResponseTO tObject)
			throws ObjectNotFoundException {
		final EarlyAlertResponse model = super.from(tObject);

		model.setComment(tObject.getComment());
		model.setEarlyAlertOutcomeOtherDescription(tObject
				.getEarlyAlertOutcomeOtherDescription());

		if (tObject.getEarlyAlertId() != null) {
			model.setEarlyAlert(earlyAlertService.get(tObject.getEarlyAlertId()));
		}

		if (tObject.getEarlyAlertOutcomeId() != null) {
			model.setEarlyAlertOutcome(earlyAlertOutcomeService.get(tObject
					.getEarlyAlertOutcomeId()));
		}

		model.setEarlyAlertOutreachIds(new HashSet<EarlyAlertOutreach>());
		if (tObject.getEarlyAlertOutreachIds() != null) {
			for (final UUID obj : tObject.getEarlyAlertOutreachIds()) {
				model.getEarlyAlertOutreachIds().add(
						earlyAlertOutreachService.load(obj));
			}
		}

		model.setEarlyAlertReferralIds(new HashSet<EarlyAlertReferral>());
		if (tObject.getEarlyAlertReferralIds() != null) {
			for (final UUID obj : tObject.getEarlyAlertReferralIds()) {
				model.getEarlyAlertReferralIds().add(
						earlyAlertReferralService.load(obj));
			}
		}

		return model;
	}
}