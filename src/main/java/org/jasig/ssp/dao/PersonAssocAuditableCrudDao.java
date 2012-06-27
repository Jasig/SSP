package org.jasig.ssp.dao;

import java.util.UUID;

import org.jasig.ssp.model.PersonAssocAuditable;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface PersonAssocAuditableCrudDao<T extends PersonAssocAuditable>
		extends AuditableCrudDao<T> {

	PagingWrapper<T> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP);
}
