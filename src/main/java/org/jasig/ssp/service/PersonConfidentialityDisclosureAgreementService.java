package org.jasig.ssp.service;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonConfidentialityDisclosureAgreement;
import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;

public interface PersonConfidentialityDisclosureAgreementService
		extends
		PersonAssocAuditableService<PersonConfidentialityDisclosureAgreement> {

	/**
	 * Has the student agreed to the most recent
	 * ConfidentialityDisclosureAgreement
	 * 
	 * @param student
	 *            Student
	 * @return Latest, agreed agreement, or null if student has not agreed to
	 *         the latest agreement.
	 */
	PersonConfidentialityDisclosureAgreement hasStudentAgreedToLatest(
			Person student);

	/**
	 * Has the student agreed to at least one ConfidentialityDisclosureAgreement
	 * 
	 * @param student
	 *            Student
	 * @return Returns null if student has not agreed to any agreements.
	 *         Otherwise returns one of them, though it is not defined which one
	 *         is returned.
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
	 * @return Returns the agreement if student has already agreed to the
	 *         agreement, otherwise null.
	 */
	PersonConfidentialityDisclosureAgreement hasStudentAgreedTo(Person student,
			ConfidentialityDisclosureAgreement agreement);

	/**
	 * Return the most recent agreement
	 * 
	 * @exception ObjectNotFoundException
	 *                if latest agreement could not be found.
	 * @return The most recent agreement.
	 */
	ConfidentialityDisclosureAgreement latestAgreement()
			throws ObjectNotFoundException;

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
	 * @exception ObjectNotFoundException
	 *                if latest agreement could not be found.
	 */
	void studentAgreed(Person student) throws ObjectNotFoundException;
}