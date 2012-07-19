package org.jasig.ssp.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.transferobject.PersonSearchResultTO;
import org.jasig.ssp.web.api.PersonSearchController;

/**
 * PersonSearchResult model for use by {@link PersonSearchResultTO} and then
 * {@link PersonSearchController}.
 */
public class PersonSearchResult {

	// id of the student
	private UUID id;

	private String schoolId;

	private String firstName;

	private String middleInitial;

	private String lastName;

	private String photoUrl;

	private String currentProgramStatusName;

	private Person coach;

	public PersonSearchResult() {
		super();
	}

	/**
	 * Fill a new search result with the specified {@link Person}.
	 * 
	 * @param person
	 *            the person
	 * @param personProgramStatusService
	 *            PersonProgramStatus for looking up data.
	 * @throws ObjectNotFoundException
	 *             If Person was not found when looking up data.
	 */
	public PersonSearchResult(@NotNull final Person person,
			@NotNull final PersonProgramStatusService personProgramStatusService)
			throws ObjectNotFoundException {
		super();

		if (null == person) {
			return;
		}

		id = person.getId();
		schoolId = person.getSchoolId();
		firstName = person.getFirstName();
		middleInitial = person.getMiddleInitial();
		lastName = person.getLastName();
		photoUrl = person.getPhotoUrl();

		final PersonProgramStatus pps = personProgramStatusService
				.getCurrent(id);
		if (pps != null) {
			currentProgramStatusName = pps.getProgramStatus().getName();
		}

		coach = person.getCoach();
	}

	public UUID getId() {
		return id;
	}

	public void setId(@NotNull final UUID id) {
		this.id = id;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(@NotNull final String schoolId) {
		this.schoolId = schoolId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(@NotNull final String firstName) {
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

	public void setLastName(@NotNull final String lastName) {
		this.lastName = lastName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(final String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/**
	 * @return the currentProgramStatusName
	 */
	public String getCurrentProgramStatusName() {
		return currentProgramStatusName;
	}

	/**
	 * @param currentProgramStatusName
	 *            the currentProgramStatusName to set; optional
	 */
	public void setCurrentProgramStatusName(
			final String currentProgramStatusName) {
		this.currentProgramStatusName = currentProgramStatusName;
	}

	/**
	 * @return the coach
	 */
	public Person getCoach() {
		return coach;
	}

	/**
	 * @param coach
	 *            the coach to set; optional
	 */
	public void setCoach(final Person coach) {
		this.coach = coach;
	}
}