/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao.external;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalStudentSpecialServiceGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExternalStudentSpecialServiceGroupDao extends
		AbstractExternalDataDao<ExternalStudentSpecialServiceGroup> {

	public ExternalStudentSpecialServiceGroupDao() {
		super(ExternalStudentSpecialServiceGroup.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExternalStudentSpecialServiceGroup> getStudentSpecialServiceGroups(String schoolId){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));
		criteria.addOrder(Order.asc("code"));
		return (List<ExternalStudentSpecialServiceGroup>)criteria.list();
	}

	public List<ExternalStudentSpecialServiceGroup> getStudentSpecialServiceGroupsBySchoolIds(List<String> schoolIds){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.in("schoolId", schoolIds));
		criteria.addOrder(Order.asc("schoolId"));

		return (List<ExternalStudentSpecialServiceGroup>)criteria.list();
	}

    public List<String> getAllSchoolIdsWithSpecifiedSSGs(List<String> ssgCodeParams) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.in("code", ssgCodeParams));
        criteria.addOrder(Order.asc("schoolId"));
        criteria.setProjection(Projections.groupProperty("schoolId"));

        return (List<String>) criteria.list();
    }
}