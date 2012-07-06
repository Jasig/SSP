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