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
 
 /*
 * IRSC CUSTOMIZATIONS
 * 06/12/2014 - Jonathan Hart IRSC TAPS 20140039 - Created EarlyAlertInterventionTOFactoryImpl.java
 */
package org.jasig.ssp.factory.reference.impl;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.reference.EarlyAlertInterventionDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertInterventionTOFactory;
import org.jasig.ssp.model.reference.EarlyAlertIntervention;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.EarlyAlertInterventionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertIntervention transfer object factory implementation class for
 * converting back and forth from EarlyAlertIntervention models.
 * 
 * Based on EarlyAlertSuggestion By @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class EarlyAlertInterventionTOFactoryImpl
		extends
		AbstractReferenceTOFactory<EarlyAlertInterventionTO, EarlyAlertIntervention>
		implements EarlyAlertInterventionTOFactory {

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	public EarlyAlertInterventionTOFactoryImpl() {
		super(EarlyAlertInterventionTO.class, EarlyAlertIntervention.class);
	}

	@Autowired
	private transient EarlyAlertInterventionDao dao;

	@Override
	protected EarlyAlertInterventionDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertIntervention from(
			@NotNull final EarlyAlertInterventionTO tObject)
			throws ObjectNotFoundException {
		final EarlyAlertIntervention model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());

		return model;
	}
}
