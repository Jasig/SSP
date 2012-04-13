package org.studentsuccessplan.ssp.service;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;

public interface PersonConfidentialityDisclosureAgreementService {

	/**
	 * Has the student agreed to the most recent
	 * ConfidentialityDisclosureAgreement
	 * 
	 * @param student
	 *            Student
	 * @return
	 */
	PersonConfidentialityDisclosureAgreement hasStudentAgreedToLatest(
			Person student);

	/**
	 * Has the student agreed to at least one ConfidentialityDisclosureAgreement
	 * 
	 * @param student
	 *            Student
	 * @return
	 */
	PersonConfidentialityDisclosureAgreement hasStudentAgreedToOne(
			Person student);

	/**
	 * Has the student agreed to a specific agreement?
	 * 
	 * @param student
	 *            Student
	 * @param agreement
	 *            Agreement
	 * @return
	 */
	PersonConfidentialityDisclosureAgreement hasStudentAgreedTo(Person student,
			ConfidentialityDisclosureAgreement agreement);

	/**
	 * Return the most recent agreement
	 * 
	 * @return The most recent agreement
	 */
	ConfidentialityDisclosureAgreement latestAgreement();

	/**
	 * Mark the given student as agreeing to the agreement
	 * 
	 * @param student
	 *            Student
	 * @param agreement
	 *            Agreement
	 */
	void studentAgreedTo(Person student,
			ConfidentialityDisclosureAgreement agreement);

	/**
	 * Mark the given student as agreeing to the most recent agreement
	 * 
	 * @param student
	 *            Student to be marked as agreeing to the most recent agreement
	 */
	void studentAgreed(Person student);
}