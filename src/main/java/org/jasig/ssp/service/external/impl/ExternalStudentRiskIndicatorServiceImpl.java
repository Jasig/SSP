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
package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalStudentRiskIndicatorDao;
import org.jasig.ssp.model.external.ExternalStudentRiskIndicator;
import org.jasig.ssp.service.external.ExternalStudentRiskIndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
public class ExternalStudentRiskIndicatorServiceImpl extends
		AbstractExternalDataService<ExternalStudentRiskIndicator> implements ExternalStudentRiskIndicatorService {

	@Autowired
	private transient ExternalStudentRiskIndicatorDao dao;

	@Override
	protected ExternalDataDao<ExternalStudentRiskIndicator> getDao() {
		return dao;
	}

	@Override
	public List<ExternalStudentRiskIndicator> getStudentTestResults(String schoolId) {
		return dao.getStudentRiskIndicators(schoolId);
	}
}
