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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasig.ssp.dao.TemplateDao;
import org.jasig.ssp.model.AbstractPlanCourse;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.PlanCourse;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.model.TemplateCourse;
import org.jasig.ssp.service.TemplateService;
import org.jasig.ssp.transferobject.TemplateTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * 
 * 
 * @author tony.arland
 */
@Service
@Transactional
public  class TemplateServiceImpl extends AbstractPlanServiceImpl<Template,TemplateTO> implements TemplateService {

	@Autowired
	private transient TemplateDao dao;
	
	@Override
	protected TemplateDao getDao() {
		return dao;
	}

	@Override
	public PagingWrapper<Template> getAll(
			SortingAndPaging createForSingleSortWithPaging, Boolean isPrivate,
			String divisionCode, String programCode, String departmentCode) {
		return getDao().getAll(createForSingleSortWithPaging,  isPrivate,
			 divisionCode,  programCode, departmentCode);
	}

}