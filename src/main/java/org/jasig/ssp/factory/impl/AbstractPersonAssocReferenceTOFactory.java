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
package org.jasig.ssp.factory.impl;

import java.util.Collection;
import java.util.Set;

import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonAssocAuditable;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.AbstractAuditableTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;
import org.jasig.ssp.util.SetOps;

import com.google.common.collect.Sets;

public abstract class AbstractPersonAssocReferenceTOFactory<TObject extends AbstractAuditableTO<M>, M extends PersonAssocAuditable, R extends AbstractReference>
		extends AbstractAuditableTOFactory<TObject, M> {

	public AbstractPersonAssocReferenceTOFactory(
			final Class<TObject> transferObjectClass,
			final Class<M> persistentClass) {
		super(transferObjectClass, persistentClass);
	}

	public abstract M fromLite(
			final ReferenceLiteTO<R> lite,
			final Person person) throws ObjectNotFoundException;

	public Set<M> fromLites(
			final Collection<ReferenceLiteTO<R>> lites,
			final Person person) throws ObjectNotFoundException {

		final Set<M> results = Sets
				.newHashSet();

		for (final ReferenceLiteTO<R> lite : lites) {
			results.add(fromLite(lite, person));
		}

		return results;
	}

	public void updateSetFromLites(
			final Set<M> updateSet,
			final Collection<ReferenceLiteTO<R>> lites,
			final Person person) throws ObjectNotFoundException {

		if (((lites == null) || lites.isEmpty())) {
			SetOps.softDeleteSetItems(updateSet);

		} else {
			final Set<M> replacingWith = fromLites(lites, person);
			SetOps.updateSet(updateSet, replacingWith);
		}
	}
}