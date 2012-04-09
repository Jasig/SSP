package edu.sinclair.ssp.service;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonConfidentialityDisclosureAgreement;
import edu.sinclair.ssp.model.reference.ConfidentialityDisclosureAgreement;

public interface PersonConfidentialityDisclosureAgreementService {

	/**
	 * Has the student agreed to the most recent
	 * ConfidentialityDisclosureAgreement
	 */
	public PersonConfidentialityDisclosureAgreement hasStudentAgreedToLatest(
			Person student);

	/**
	 * Has the student agreed to at least one
	 * ConfidentialityDisclosureAgreement
	 */
	public PersonConfidentialityDisclosureAgreement hasStudentAgreedToOne(
			Person student);

	/**
	 * Has the student agreed to a specific agreement?
	 */
	public PersonConfidentialityDisclosureAgreement hasStudentAgreedTo(
			Person student, ConfidentialityDisclosureAgreement agreement);

	/**
	 * Return the most recent agreement
	 */
	public ConfidentialityDisclosureAgreement latestAgreement();

	/**
	 * Mark the given student as agreeing to the agreement
	 */
	public void studentAgreedTo(Person student,
			ConfidentialityDisclosureAgreement agreement);

	/**
	 * Mark the given student as agreeing to the most recent agreement
	 */
	public void studentAgreed(Person student);

}