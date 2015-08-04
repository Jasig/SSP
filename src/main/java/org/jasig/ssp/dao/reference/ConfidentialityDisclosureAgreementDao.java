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
package org.jasig.ssp.dao.reference;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.jasig.ssp.transferobject.CaseloadReassignmentRequestTO;
import org.jasig.ssp.transferobject.reference.ConfidentialityDisclosureAgreementTO;
import org.jasig.ssp.util.hibernate.BatchProcessor;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Lists;

/**
 * Data access class for the ConfidentialityDisclosureAgreement reference
 * entity.
 */
@Repository
public class ConfidentialityDisclosureAgreementDao extends
		AbstractReferenceAuditableCrudDao<ConfidentialityDisclosureAgreement>
		implements AuditableCrudDao<ConfidentialityDisclosureAgreement> {

	public ConfidentialityDisclosureAgreementDao() {
		super(ConfidentialityDisclosureAgreement.class);
	}
	
	@Override
	public PagingWrapper<ConfidentialityDisclosureAgreement> getAll(final SortingAndPaging sAndP) {
		SortingAndPaging sp = sAndP;
		if (sp == null) {
			sp = new SortingAndPaging(ObjectStatus.ACTIVE);
		}

		if (!sp.isSorted()) {
			sp.appendSortField("name", SortDirection.ASC);
		}

		return super.getAll(sp);
	}
	
	public int setEnabled(final UUID id) {
			
		String sqlupdate = "update ConfidentialityDisclosureAgreement set enabled = TRUE where id = :id";
		int t =  createHqlQuery( sqlupdate ).setParameter("id", id).executeUpdate();		
		
		//remove existing CDAs
		String sqlremove = "update ConfidentialityDisclosureAgreement set enabled = FALSE where id != :id";
		createHqlQuery( sqlremove ).setParameter("id", id).executeUpdate();
		
		return t;
	}
	
	@Override
	public ConfidentialityDisclosureAgreement save(ConfidentialityDisclosureAgreement t) {
		final Session session = sessionFactory.getCurrentSession();
		if (t.getId() == null) {
			session.saveOrUpdate(t);
			session.flush(); // make sure constraint violations are checked now			
		}
		
		if(t.isEnabled()) {
			//remove existing CDAs
			String sqlremove = "update ConfidentialityDisclosureAgreement set enabled = FALSE where id != :id";
			createHqlQuery( sqlremove ).setParameter("id", t.getId()).executeUpdate();
		}
		
		return t; 
	}

	public ConfidentialityDisclosureAgreementTO getLiveCDA() {
		Criteria criteria = createCriteria();		
		criteria.add(Restrictions.eq("enabled", true));
		
		List results = criteria.list();
		if(results.size() <=0) return null;		
		ConfidentialityDisclosureAgreementTO cdaTO = new ConfidentialityDisclosureAgreementTO((ConfidentialityDisclosureAgreement)(results.get(0)));
		return cdaTO;
	}
	
	
	
	
	
}
