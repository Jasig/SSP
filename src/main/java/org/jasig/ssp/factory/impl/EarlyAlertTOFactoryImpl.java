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

import org.jasig.ssp.dao.EarlyAlertDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.EarlyAlertTOFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.EarlyAlertReasonService;
import org.jasig.ssp.service.reference.EarlyAlertSuggestionService;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.reference.EarlyAlertReasonTO;
import org.jasig.ssp.transferobject.reference.EarlyAlertSuggestionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlert transfer object factory
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional(readOnly = true)
public class EarlyAlertTOFactoryImpl extends
		AbstractAuditableTOFactory<EarlyAlertTO, EarlyAlert>
		implements EarlyAlertTOFactory {

	/**
	 * Construct an EarlyAlert transfer object factory with the specific class
	 * types for use by the super class methods.
	 */
	public EarlyAlertTOFactoryImpl() {
		super(EarlyAlertTO.class, EarlyAlert.class);
	}

	@Autowired
	private transient EarlyAlertDao dao;

	@Autowired
	private transient CampusService campusService;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient EarlyAlertReasonService earlyAlertReasonService;

	@Autowired
	private transient EarlyAlertSuggestionService earlyAlertSuggestionService;

	@Override
	protected EarlyAlertDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlert from(final EarlyAlertTO tObject)
			throws ObjectNotFoundException {
		final EarlyAlert model = super.from(tObject);

		model.setCourseName(tObject.getCourseName());
		model.setCourseTitle(tObject.getCourseTitle());
		model.setCourseTermCode(tObject.getCourseTermCode());
		model.setEmailCC(tObject.getEmailCC());
		
		if (tObject.getCampusId() != null) {
			model.setCampus(campusService.get(tObject.getCampusId()));
		}
		
		model.setEarlyAlertReasonOtherDescription(tObject
				.getEarlyAlertReasonOtherDescription());
		model.setEarlyAlertSuggestionOtherDescription(tObject
				.getEarlyAlertSuggestionOtherDescription());
		model.setComment(tObject.getComment());
		model.setClosedDate(tObject.getClosedDate());
		if ( tObject.getClosedById() != null ) {
			model.setClosedBy(personService.get(tObject.getClosedById()));
		}

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		model.setEarlyAlertReasonIds(new HashSet<EarlyAlertReason>());
		if (tObject.getEarlyAlertReasonIds() != null) {
			for (final EarlyAlertReasonTO obj : tObject
					.getEarlyAlertReasonIds()) {
				model.getEarlyAlertReasonIds().add(
						earlyAlertReasonService.load(obj.getId()));
			}
		}

		model.setEarlyAlertSuggestionIds(new HashSet<EarlyAlertSuggestion>());
		if (tObject.getEarlyAlertSuggestionIds() != null) {
			for (final EarlyAlertSuggestionTO obj : tObject
					.getEarlyAlertSuggestionIds()) {
				model.getEarlyAlertSuggestionIds().add(
						earlyAlertSuggestionService.load(obj.getId()));
			}
		}

		return model;
	}
}