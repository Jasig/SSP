package org.jasig.ssp.service;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface RestrictedPersonAssocAuditableService<T extends RestrictedPersonAssocAuditable>
		extends PersonAssocAuditableService<T> {

	PagingWrapper<T> getAllForPerson(Person person,
			SspUser requestor,
			SortingAndPaging sAndP);

	@Override
	@Deprecated
	PagingWrapper<T> getAllForPerson(Person person,
			SortingAndPaging sAndP);
}
