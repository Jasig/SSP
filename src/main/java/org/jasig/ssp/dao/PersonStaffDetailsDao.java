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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.PersonStaffDetails;
import org.jasig.ssp.model.ObjectStatus;
import org.springframework.stereotype.Repository;

/**
 * CRUD methods for the PersonStaffDetails model.
 */
@Repository
public class PersonStaffDetailsDao extends
		AbstractAuditableCrudDao<PersonStaffDetails> implements
		AuditableCrudDao<PersonStaffDetails> {

	/**
	 * Constructor
	 */
	public PersonStaffDetailsDao() {
		super(PersonStaffDetails.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAllHomeDepartments(ObjectStatus status){
		Criteria criteria = createCriteria();
		if(status != null)
			criteria.add(Restrictions.eq("objectStatus", status));
		criteria.add(Restrictions.isNotNull("departmentName"));
		criteria.setProjection(Projections.projectionList().add(Projections.distinct(Projections.property("departmentName"))));
		return ((List<String>)criteria.list());
	}
	
	
}