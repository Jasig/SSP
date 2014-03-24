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
package org.jasig.ssp.factory.reference.impl;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.reference.EarlyAlertReferralDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertReferralTOFactory;
import org.jasig.ssp.model.reference.EarlyAlertReferral;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.EarlyAlertReferralTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertReferral transfer object factory implementation class for
 * converting back and forth from EarlyAlertReferral models.
 * 
 * @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class EarlyAlertReferralTOFactoryImpl
		extends
		AbstractReferenceTOFactory<EarlyAlertReferralTO, EarlyAlertReferral>
		implements EarlyAlertReferralTOFactory {

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	public EarlyAlertReferralTOFactoryImpl() {
		super(EarlyAlertReferralTO.class, EarlyAlertReferral.class);
	}

	@Autowired
	private transient EarlyAlertReferralDao dao;

	@Override
	protected EarlyAlertReferralDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertReferral from(
			@NotNull final EarlyAlertReferralTO tObject)
			throws ObjectNotFoundException {
		final EarlyAlertReferral model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());
		model.setAcronym(tObject.getAcronym());
		
		model.setRecipientEmailAddress(tObject.getRecipientEmailAddress());
		model.setCarbonCopy(tObject.getCarbonCopy());

		return model;
	}
}
