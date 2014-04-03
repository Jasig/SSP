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

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.jasig.ssp.model.MapTemplateVisibility;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.transferobject.TemplateSearchTO;
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
			SortingAndPaging sNp,
			TemplateSearchTO searchTO) {
		
				Criteria criteria = createCriteria(sNp);
				LogicalExpression isPrivate =null;
				SimpleExpression anonymous =  Restrictions.eq("visibility", MapTemplateVisibility.ANONYMOUS);
				SimpleExpression authenticated =Restrictions.eq("visibility", MapTemplateVisibility.AUTHENTICATED);
				
				if(searchTO.visibilityAll() || searchTO.getVisibility().equals( MapTemplateVisibility.PRIVATE)){
					 isPrivate =  Restrictions.and(Restrictions.eq("visibility", MapTemplateVisibility.PRIVATE), 
						Restrictions.eq("owner", getSecurityService().currentlyAuthenticatedUser().getPerson()));
				}
				
				if(searchTO.visibilityAll()){
					criteria.add(Restrictions.or(Restrictions.or(authenticated, anonymous), isPrivate));
				} else if(searchTO.getVisibility().equals( MapTemplateVisibility.PRIVATE)){
					criteria.add(isPrivate);
				}else if(searchTO.getVisibility().equals( MapTemplateVisibility.AUTHENTICATED)){
					criteria.add(authenticated);
				}else if(searchTO.getVisibility().equals( MapTemplateVisibility.ANONYMOUS)){
					criteria.add(anonymous);
				}
				
				
				if(!StringUtils.isEmpty(searchTO.getProgramCode()))
				{
					criteria.add(Restrictions.eq("programCode", searchTO.getProgramCode()));
				}
				if(!StringUtils.isEmpty(searchTO.getDivisionCode()))
				{
					criteria.add(Restrictions.eq("divisionCode", searchTO.getDivisionCode()));
				}					
				if(!StringUtils.isEmpty(searchTO.getDepartmentCode()))
				{
					criteria.add(Restrictions.eq("departmentCode", searchTO.getDepartmentCode()));
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

	public Person getOwnerForPlan(UUID id) {
		String query = "Select t.owner from org.jasig.ssp.model.Template t where t.id = :id";
		return (Person) createHqlQuery(query).setParameter("id", id).uniqueResult();
	}
}
