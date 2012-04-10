package org.studentsuccessplan.ssp.service.impl;

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
import org.studentsuccessplan.ssp.service.PersonConfidentialityDisclosureAgreementService;

@Service
@Transactional(readOnly = true)
public class PersonConfidentialityDisclosureAgreementServiceImpl implements PersonConfidentialityDisclosureAgreementService {

	@Autowired
	private PersonConfidentialityDisclosureAgreementDao dao;

	@Autowired
	private ConfidentialityDisclosureAgreementDao cdaDao;

	/**
	 * Has the student agreed to the most recent
	 * ConfidentialityDisclosureAgreement
	 */
	@Override
	public PersonConfidentialityDisclosureAgreement hasStudentAgreedToLatest(
			Person student) {
		throw new RuntimeException("not implemented");
	}

	/**
	 * Has the student agreed to at least one
	 * ConfidentialityDisclosureAgreement
	 */
	@Override
	public PersonConfidentialityDisclosureAgreement hasStudentAgreedToOne(
			Person student) {
		List<PersonConfidentialityDisclosureAgreement> agreements = dao
				.forStudent(student);
		if ((agreements != null) && (agreements.size() > 0)) {
			return agreements.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Has the student agreed to a specific agreement?
	 */
	@Override
	public PersonConfidentialityDisclosureAgreement hasStudentAgreedTo(
			Person student, ConfidentialityDisclosureAgreement agreement) {
		return dao.forStudentAndAgreement(student, agreement);
	}

	/**
	 * Return the most recent agreement
	 */
	@Override
	public ConfidentialityDisclosureAgreement latestAgreement() {
		List<ConfidentialityDisclosureAgreement> agreements =
				cdaDao.getAll(ObjectStatus.ACTIVE);
		if ((agreements != null) && (agreements.size() > 0)) {
			return agreements.get(0);
		} else {
			return null;
		}
		// :TODO should we create an active flag on
		// ConfidentialityDisclosureAgreement?
	}

	/**
	 * Mark the given student as agreeing to the agreement
	 */
	@Override
	public void studentAgreedTo(Person student,
			ConfidentialityDisclosureAgreement agreement) {
		if (null == hasStudentAgreedTo(student, agreement)) {
			// create new
			PersonConfidentialityDisclosureAgreement pAgreement = new PersonConfidentialityDisclosureAgreement();
			pAgreement.setConfidentialityDisclosureAgreement(agreement);
			pAgreement.setPerson(student);
			dao.save(pAgreement);
		}// otherwise ignore
	}

	/**
	 * Mark the given student as agreeing to the most recent agreement
	 */
	@Override
	public void studentAgreed(Person student) {
		studentAgreedTo(student, latestAgreement());
	}
}
