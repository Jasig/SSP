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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonConfidentialityDisclosureAgreement;
import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.springframework.stereotype.Repository;

@Repository
public class PersonConfidentialityDisclosureAgreementDao
		extends
		AbstractPersonAssocAuditableCrudDao<PersonConfidentialityDisclosureAgreement>
		implements
		PersonAssocAuditableCrudDao<PersonConfidentialityDisclosureAgreement> {

	public PersonConfidentialityDisclosureAgreementDao() {
		super(PersonConfidentialityDisclosureAgreement.class);
	}

	@SuppressWarnings(UNCHECKED)
	public List<PersonConfidentialityDisclosureAgreement> forStudent(
			final Person student) {
		final Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonConfidentialityDisclosureAgreement.class)
				.add(Restrictions.eq("person", student))
				.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE))
				.addOrder(Order.desc("createdDate"));
		return query.list();
	}

	public PersonConfidentialityDisclosureAgreement forStudentAndAgreement(
			final Person student,
			final ConfidentialityDisclosureAgreement agreement) {
		final Criteria query = sessionFactory
				.getCurrentSession()
				.createCriteria(PersonConfidentialityDisclosureAgreement.class)
				.add(Restrictions.eq("person", student))
				.add(Restrictions.eq("confidentialityDisclosureAgreement",
						agreement))
				.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE))
				.addOrder(Order.desc("createdDate"));
		return (PersonConfidentialityDisclosureAgreement) query.uniqueResult();
	}

}
