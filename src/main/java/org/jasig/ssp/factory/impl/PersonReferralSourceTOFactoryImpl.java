package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.PersonReferralSourceDao;
import org.jasig.ssp.factory.PersonReferralSourceTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonReferralSource;
import org.jasig.ssp.model.reference.ReferralSource;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.transferobject.PersonReferralSourceTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonReferralSourceTOFactoryImpl
		extends
		AbstractPersonAssocReferenceTOFactory<PersonReferralSourceTO, PersonReferralSource, ReferralSource>
		implements PersonReferralSourceTOFactory {

	public PersonReferralSourceTOFactoryImpl() {
		super(PersonReferralSourceTO.class,
				PersonReferralSource.class);
	}

	@Autowired
	private transient PersonReferralSourceDao dao;

	@Autowired
	private transient ReferralSourceService service;

	@Autowired
	private transient PersonService personService;

	@Override
	protected PersonReferralSourceDao getDao() {
		return dao;
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonReferralSourceTOFactoryImpl.class);

	@Override
	public PersonReferralSource from(
			final PersonReferralSourceTO tObject)
			throws ObjectNotFoundException {
		final PersonReferralSource model = super.from(tObject);

		model.setReferralSource(tObject.getReferralSourceId() == null ? null
				: service.get(tObject.getReferralSourceId()));

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		return model;
	}

	@Override
	public PersonReferralSource fromLite(
			final ReferenceLiteTO<ReferralSource> lite,
			final Person person) throws ObjectNotFoundException {

		final PagingWrapper<PersonReferralSource> results = dao
				.getAllForPersonIdAndReferralSourceId(person.getId(),
						lite.getId(), new SortingAndPaging(ObjectStatus.ACTIVE));

		if (results.getResults() > 1) {
			LOGGER.error("Multiple active PersonReferralSources found for Person: "
					+ person.getId() + ", ReferralSource:" + lite.getId());
			return results.getRows().iterator().next();
		} else if (results.getResults() == 1) {
			return results.getRows().iterator().next();
		}

		// else
		final PersonReferralSource pssg = new PersonReferralSource();
		pssg.setPerson(person);
		pssg.setReferralSource(service.get(lite.getId()));
		return pssg;
	}
}