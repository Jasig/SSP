/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.RestrictedPersonAssocAuditableDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public abstract class AbstractRestrictedPersonAssocAuditableService<T extends RestrictedPersonAssocAuditable>
		extends AbstractPersonAssocAuditableService<T>
		implements RestrictedPersonAssocAuditableService<T> {

	@Override
	public List<T> get(final List<UUID> ids, final SspUser requester,
			final SortingAndPaging sAndP) {
		return getDao().get(ids, requester, sAndP);
	}

	@Override
	public PagingWrapper<T> getAllForPerson(final Person person,
			final SspUser requestor,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), requestor, sAndP);
	}

	/**
	 * this method will throw UnsupportedOperationException. Use getAllForPerson
	 * with person, requestor and sAndP instead.
	 */
	@Override
	public PagingWrapper<T> getAllForPerson(final Person person,
			final SortingAndPaging sAndP) {
		throw new UnsupportedOperationException(
				"For Restricted Person Associated Auditables, you must call getAllForPersonId and supply a requestor");
	}

	@Override
	protected abstract RestrictedPersonAssocAuditableDao<T> getDao();

}
