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
package org.jasig.ssp.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateDao extends AbstractPlanDao<Template> implements
		AuditableCrudDao<Template> {

	public TemplateDao() {
		super(Template.class);
	}
	@Autowired
	private transient SecurityService securityService;
	
	public PagingWrapper<Template> getAll(
			SortingAndPaging sNp, Boolean isPrivate,
			String divisionCode, String programCode, String departmentCode) {
		
				Criteria criteria = createCriteria(sNp);
				if(isPrivate != null)
				{
					criteria.add(Restrictions.eq("isPrivate", isPrivate));
					if(isPrivate)
					{
						criteria.add(Restrictions.eq("owner", getSecurityService().currentlyAuthenticatedUser()));
					}
				}
				if(!StringUtils.isEmpty(programCode))
				{
					criteria.add(Restrictions.eq("programCode", programCode));
				}
				if(!StringUtils.isEmpty(divisionCode))
				{
					criteria.add(Restrictions.eq("divisionCode", divisionCode));
				}					
				if(!StringUtils.isEmpty(departmentCode))
				{
					criteria.add(Restrictions.eq("departmentCode", departmentCode));
				}
				return processCriteriaWithStatusSortingAndPaging(criteria,
				 				sNp);
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}
