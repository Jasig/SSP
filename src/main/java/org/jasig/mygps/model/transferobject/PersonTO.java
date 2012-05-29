package org.jasig.mygps.model.transferobject;

import java.io.Serializable;

import org.jasig.ssp.model.Person;

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

	/**
	 * Empty constructor
	 */
	public PersonTO() {
		/* empty constructor */
	}

	public PersonTO(final String id) {
		this.id = id;
	}

	private static final int DEPTH_LIMIT = 1;

	public PersonTO(final Person person, final int depth) {
		id = person.getId().toString();
		firstName = person.getFirstName();
		lastName = person.getLastName();
		phoneNumber = person.getHomePhone();
		emailAddress = person.getPrimaryEmailAddress();
		photoUrl = person.getPhotoUrl();

		if ((null != person.getCoach())
				&& (person.getId() != person.getCoach()
						.getId()) && (depth < DEPTH_LIMIT)) {
			coach = new PersonTO(person.getCoach(), depth + 1);
		}

		strengths = person.getStrengths();
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(final String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(final String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public PersonTO getCoach() {
		return coach;
	}

	public void setCoach(final PersonTO coach) {
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
	public void setStrengths(final String strengths) {
		this.strengths = strengths;
	}
}