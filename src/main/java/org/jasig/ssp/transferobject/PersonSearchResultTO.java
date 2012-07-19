package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.PersonSearchResult;

/**
 * PersonSearchResult transfer object
 */
public class PersonSearchResultTO extends PersonSearchResult implements
		TransferObject<PersonSearchResult> {

	public PersonSearchResultTO(final PersonSearchResult model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonSearchResult model) {
		setFirstName(model.getFirstName());
		setLastName(model.getLastName());
		setMiddleInitial(model.getMiddleInitial());
		setPhotoUrl(model.getPhotoUrl());
		setSchoolId(model.getSchoolId());
		setId(model.getId());
		setCurrentProgramStatusName(model.getCurrentProgramStatusName());
		setCoach(model.getCoach());
	}
}