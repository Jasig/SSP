package org.jasig.ssp.dao;

import java.util.UUID;

import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface RestrictedPersonAssocAuditableDao<T extends RestrictedPersonAssocAuditable>
		extends PersonAssocAuditableCrudDao<T> {

	PagingWrapper<T> getAllForPersonId(final UUID personId,
			final SspUser requestor,
			final SortingAndPaging sAndP);
}
