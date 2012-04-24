package org.studentsuccessplan.ssp.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.PersonConfidentialityDisclosureAgreementDao;
import org.studentsuccessplan.ssp.dao.reference.ConfidentialityDisclosureAgreementDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonConfidentialityDisclosureAgreementService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Sets;

@Service
@Transactional(readOnly = true)
public class PersonConfidentialityDisclosureAgreementServiceImpl implements
		PersonConfidentialityDisclosureAgreementService {

	@Autowired
	private PersonConfidentialityDisclosureAgreementDao dao;

	@Autowired
	private ConfidentialityDisclosureAgreementDao cdaDao;

	@Override
	public PersonConfidentialityDisclosureAgreement hasStudentAgreedToLatest(
			Person student) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public PersonConfidentialityDisclosureAgreement hasStudentAgreedToOne(
			Person student) {
		List<PersonConfidentialityDisclosureAgreement> agreements = dao
				.forStudent(student);

		if ((agreements != null) && !agreements.isEmpty()) {
			return agreements.get(0);
		}

		return null;
	}

	@Override
	public PersonConfidentialityDisclosureAgreement hasStudentAgreedTo(
			Person student, ConfidentialityDisclosureAgreement agreement) {
		return dao.forStudentAndAgreement(student, agreement);
	}

	@Override
	public ConfidentialityDisclosureAgreement latestAgreement()
			throws ObjectNotFoundException {
		List<ConfidentialityDisclosureAgreement> agreements = cdaDao
				.getAll(SortingAndPaging
						.createForSingleSort(ObjectStatus.ACTIVE, 0, 1,
								"modifiedDate", "DESC", null));
		if ((agreements != null) && (!agreements.isEmpty())) {
			return agreements.get(0);
		} else {
			throw new ObjectNotFoundException(
					"Latest Confidentiality Agreement could not be found.");
		}
		// :TODO should we create an active flag on
		// ConfidentialityDisclosureAgreement?
	}

	@Override
	public void studentAgreedTo(Person student,
			ConfidentialityDisclosureAgreement agreement) {
		if (null == hasStudentAgreedTo(student, agreement)) {
			// create new
			PersonConfidentialityDisclosureAgreement pAgreement = new PersonConfidentialityDisclosureAgreement();
			pAgreement.setConfidentialityDisclosureAgreement(agreement);
			pAgreement.setPerson(student);
			pAgreement = dao.save(pAgreement);

			if (student.getConfidentialityDisclosureAgreements() == null) {
				HashSet<PersonConfidentialityDisclosureAgreement> confidentialityDisclosureAgreementsSet = Sets
						.newHashSet();
				student.setConfidentialityDisclosureAgreements(confidentialityDisclosureAgreementsSet);
			}

			student.getConfidentialityDisclosureAgreements().add(pAgreement);
		}// otherwise ignore
	}

	@Override
	public void studentAgreed(Person student) throws ObjectNotFoundException {
		studentAgreedTo(student, latestAgreement());
	}
}
