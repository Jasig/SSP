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

import org.apache.commons.lang.NotImplementedException;
import org.jasig.ssp.dao.MapAbstractPlanDao;
import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.service.MapAbstractPlanService;
import org.jasig.ssp.transferobject.PersonLiteTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MapAbstractPlanServiceImpl implements MapAbstractPlanService {

	@Autowired
	private MapAbstractPlanDao dao;

	@Override
	@Transactional(readOnly = true)
	public PagingWrapper<AbstractPlan> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	@Transactional(readOnly = true)
	public PagingWrapper<PersonLiteTO> getAllOwnersLite(SortingAndPaging sAndP) {
		throw new NotImplementedException("implement me!");
	}

	@Override
	@Transactional(readOnly = true)
	public PagingWrapper<AbstractPlan> getAllForOwner(SortingAndPaging sAndP) {
		throw new NotImplementedException("implement me!");
	}
}
