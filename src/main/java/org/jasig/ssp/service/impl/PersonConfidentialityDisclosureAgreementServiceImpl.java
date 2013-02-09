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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.jasig.ssp.dao.PersonConfidentialityDisclosureAgreementDao;
import org.jasig.ssp.dao.reference.ConfidentialityDisclosureAgreementDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonConfidentialityDisclosureAgreement;
import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonConfidentialityDisclosureAgreementService;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

@Service
@Transactional(readOnly = true)
public class PersonConfidentialityDisclosureAgreementServiceImpl
		extends
		AbstractPersonAssocAuditableService<PersonConfidentialityDisclosureAgreement>
		implements PersonConfidentialityDisclosureAgreementService {

	@Autowired
	private transient PersonConfidentialityDisclosureAgreementDao dao;

	@Autowired
	private transient ConfidentialityDisclosureAgreementDao cdaDao;

	@Override
	protected PersonConfidentialityDisclosureAgreementDao getDao() {
		return dao;
	}

	@Override
	public PersonConfidentialityDisclosureAgreement hasStudentAgreedToLatest(
			final Person student) {
		throw new NotImplementedException();
	}

	@Override
	public PersonConfidentialityDisclosureAgreement hasStudentAgreedToOne(
			final Person student) {
		final List<PersonConfidentialityDisclosureAgreement> agreements = dao
				.forStudent(student);

		if ((agreements != null) && !agreements.isEmpty()) {
			return agreements.get(0);
		}

		return null;
	}

	@Override
	public PersonConfidentialityDisclosureAgreement hasStudentAgreedTo(
			final Person student,
			final ConfidentialityDisclosureAgreement agreement) {
		return dao.forStudentAndAgreement(student, agreement);
	}

	@Override
	public ConfidentialityDisclosureAgreement latestAgreement()
			throws ObjectNotFoundException {
		final Collection<ConfidentialityDisclosureAgreement> agreements = cdaDao
				.getAll(SortingAndPaging
						.createForSingleSortWithPaging(ObjectStatus.ACTIVE, 0, 1,
								"modifiedDate", "DESC", null)).getRows();

		if ((agreements != null) && !agreements.isEmpty()) {
			return agreements.iterator().next();
		}

		throw new ObjectNotFoundException(
				"Latest Confidentiality Agreement could not be found.",
				"PersonConfidentialityDisclosureAgreement");

		// :TODO should we create an active flag on
		// ConfidentialityDisclosureAgreement?
	}

	@Override
	public void studentAgreedTo(final Person student,
			final ConfidentialityDisclosureAgreement agreement) {
		if (null == hasStudentAgreedTo(student, agreement)) {
			// create new
			PersonConfidentialityDisclosureAgreement pAgreement = new PersonConfidentialityDisclosureAgreement();
			pAgreement.setConfidentialityDisclosureAgreement(agreement);
			pAgreement.setPerson(student);
			pAgreement = dao.save(pAgreement);

			if (student.getConfidentialityDisclosureAgreements() == null) {
				final HashSet<PersonConfidentialityDisclosureAgreement> confidentialityDisclosureAgreementsSet = Sets
						.newHashSet();
				student.setConfidentialityDisclosureAgreements(confidentialityDisclosureAgreementsSet);
			}

			student.getConfidentialityDisclosureAgreements().add(pAgreement);
		}// otherwise ignore
	}

	@Override
	public void studentAgreed(final Person student)
			throws ObjectNotFoundException {
		studentAgreedTo(student, latestAgreement());
	}
}
