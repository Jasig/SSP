package org.studentsuccessplan.mygps.model.transferobject;

import org.studentsuccessplan.ssp.model.Person;

public class PersonTO {

	private String id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private String photoUrl;
	private PersonTO coach;

	public PersonTO() {}

	public PersonTO(String id) {
		this.id = id;
	}

	private static final int depthLimit = 1;
	public PersonTO(Person person, int depth){
		this.id = person.getId().toString();
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.phoneNumber = person.getHomePhone();
		this.emailAddress = person.getPrimaryEmailAddress();
		this.photoUrl = person.getPhotoUrl();

		if ((null != person.getDemographics().getCoach())
				&& (person.getId() != person.getDemographics().getCoach()
						.getId()) && (depth < depthLimit)) {
			this.coach = new PersonTO(person.getDemographics().getCoach(),
					depth + 1);
		}else{
			this.coach = null;
		}
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
}
