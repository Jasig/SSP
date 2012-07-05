package org.jasig.ssp.model;

import java.util.UUID;

public class PersonSearchResult {

	// id of the student
	private UUID id;

	private String schoolId, firstName, middleInitial, lastName, photoUrl;

	public PersonSearchResult() {
		super();
	}

	public PersonSearchResult(final Person person) {
		super();
		if (null == person) {
			return;
		}

		this.id = person.getId();
		this.schoolId = person.getSchoolId();
		this.firstName = person.getFirstName();
		this.middleInitial = person.getMiddleInitial();
		this.lastName = person.getLastName();
		this.photoUrl = person.getPhotoUrl();
	}

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(final String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(final String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
