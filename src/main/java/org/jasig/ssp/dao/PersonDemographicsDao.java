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

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.PersonDemographics;
import org.springframework.stereotype.Repository;

/**
 * CRUD methods for the PersonDemographics model.
 */
@Repository
public class PersonDemographicsDao extends
		AbstractAuditableCrudDao<PersonDemographics> implements
		AuditableCrudDao<PersonDemographics> {

	/**
	 * Constructor
	 */
	public PersonDemographicsDao() {
		super(PersonDemographics.class);
	}
	
	public BigDecimal getBalanceOwed(UUID personId){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" select pd.balanceOwed from PersonDemographics as pd, Person as p where p.id = :personId and pd.id = p.demographics.id");
		Query query = createHqlQuery(stringBuilder.toString());
		query.setParameter("personId", personId);
		return (BigDecimal)query.uniqueResult();
	}
}