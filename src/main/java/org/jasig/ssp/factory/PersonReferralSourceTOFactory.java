package org.jasig.ssp.factory;

import java.util.Collection;
import java.util.Set;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonReferralSource;
import org.jasig.ssp.model.reference.ReferralSource;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PersonReferralSourceTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;

public interface PersonReferralSourceTOFactory extends
		TOFactory<PersonReferralSourceTO, PersonReferralSource> {

	PersonReferralSource fromLite(
			final ReferenceLiteTO<ReferralSource> lite,
			final Person person) throws ObjectNotFoundException;

	Set<PersonReferralSource> fromLites(
			final Collection<ReferenceLiteTO<ReferralSource>> lites,
			final Person person) throws ObjectNotFoundException;

	void updateSetFromLites(
			final Set<PersonReferralSource> updateSet,
			final Collection<ReferenceLiteTO<ReferralSource>> lites,
			final Person person) throws ObjectNotFoundException;
}
