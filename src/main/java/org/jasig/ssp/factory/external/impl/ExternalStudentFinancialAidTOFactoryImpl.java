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
package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.factory.external.ExternalStudentFinancialAidTOFactory;
import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.model.external.ExternalStudentFinancialAid;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalStudentFinancialAidTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExternalStudentFinancialAidTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalStudentFinancialAidTO, ExternalStudentFinancialAid> implements
		ExternalStudentFinancialAidTOFactory {

	
	public ExternalStudentFinancialAidTOFactoryImpl() {
		super(ExternalStudentFinancialAidTO.class, ExternalStudentFinancialAid.class);
	}

	@Override
	public ExternalStudentFinancialAid from(ExternalStudentFinancialAidTO tObject) 
			throws ObjectNotFoundException {
		final ExternalStudentFinancialAid model = super.from(tObject);

		model.setSchoolId(tObject.getSchoolId());
		model.setCurrentYearFinancialAidAward(tObject.getCurrentYearFinancialAidAward());
		model.setFafsaDate(tObject.getFafsaDate());
		model.setFinancialAidGpa(tObject.getFinancialAidGpa());
		model.setFinancialAidRemaining(tObject.getFinancialAidRemaining());
		model.setGpa20AHrsNeeded(tObject.getGpa20AHrsNeeded());
		model.setGpa20BHrsNeeded(tObject.getGpa20BHrsNeeded());
		model.setNeededFor67PtcCompletion(tObject.getNeededFor67PtcCompletion());
		model.setOriginalLoanAmount(tObject.getOriginalLoanAmount());
		
		model.setRemainingLoanAmount(tObject.getRemainingLoanAmount());
		model.setSapStatus(tObject.getSapStatus());

		return model;
	}

	@Override
	protected ExternalDataDao<ExternalStudentFinancialAid> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
