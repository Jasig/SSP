package org.studentsuccessplan.mygps.model.transferobject;

import java.io.Serializable;

import org.studentsuccessplan.ssp.model.Person;

/**
 * Transfer class for {@link Person}.
 */
public class PersonTO implements Serializable {

	private static final long serialVersionUID = 7386277650707089183L;

	private String id;

	private String firstName;

	private String lastName;

	private String phoneNumber;

	private String emailAddress;

	private String photoUrl;

	private PersonTO coach;

	private String strengths;

	public PersonTO() {
	}

	public PersonTO(String id) {
		this.id = id;
	}

	private static final int depthLimit = 1;

	public PersonTO(Person person, int depth) {
		id = person.getId().toString();
		firstName = person.getFirstName();
		lastName = person.getLastName();
		phoneNumber = person.getHomePhone();
		emailAddress = person.getPrimaryEmailAddress();
		photoUrl = person.getPhotoUrl();

		if ((null != person.getDemographics().getCoach())
				&& (person.getId() != person.getDemographics().getCoach()
						.getId()) && (depth < depthLimit)) {
			coach = new PersonTO(person.getDemographics().getCoach(), depth + 1);
		} else {
			coach = null;
		}

		setStrengths(person.getStrengths());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public PersonTO getCoach() {
		return coach;
	}

	public void setCoach(PersonTO coach) {
		this.coach = coach;
	}

	/**
	 * @return the strengths
	 */
	public String getStrengths() {
		return strengths;
	}

	/**
	 * @param strengths
	 *            the strengths to set
	 */
	public void setStrengths(String strengths) {
		this.strengths = strengths;
	}
}
