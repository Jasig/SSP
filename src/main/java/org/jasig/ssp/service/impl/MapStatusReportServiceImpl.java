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
package org.jasig.ssp.service.impl;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.dao.MapStatusReportDao;
import org.jasig.ssp.dao.PersonAssocAuditableCrudDao;
import org.jasig.ssp.model.MapStatusReport;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.MapStatusReportService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Person service implementation
 * 
 * @author tony.arland
 */
@Service
@Transactional
public class MapStatusReportServiceImpl extends AbstractPersonAssocAuditableService<MapStatusReport>
		implements MapStatusReportService {

	@Autowired 
	private MapStatusReportDao dao;



	@Override
	public MapStatusReport save(MapStatusReport obj)
			throws ObjectNotFoundException, ValidationException {
		return dao.save(obj);
	}

	@Override
	public void deleteAllOldReports() {
		dao.deleteAllOldReports();
	}

	@Override
	protected PersonAssocAuditableCrudDao<MapStatusReport> getDao() {
		return dao;
	}
	

}