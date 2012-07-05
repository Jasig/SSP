package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.PersonSearchResult;

public class PersonSearchResultTO extends PersonSearchResult implements
		TransferObject<PersonSearchResult> {

	public PersonSearchResultTO(final PersonSearchResult model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonSearchResult model) {
		this.setFirstName(model.getFirstName());
		this.setLastName(model.getLastName());
		this.setMiddleInitial(model.getMiddleInitial());
		this.setPhotoUrl(model.getPhotoUrl());
		this.setSchoolId(model.getSchoolId());
		this.setId(model.getId());
	}

}
