package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.PersonServiceReasonDao;
import org.jasig.ssp.factory.PersonServiceReasonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonServiceReason;
import org.jasig.ssp.model.reference.ServiceReason;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.jasig.ssp.transferobject.PersonServiceReasonTO;
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
public class PersonServiceReasonTOFactoryImpl
		extends
		AbstractPersonAssocReferenceTOFactory<PersonServiceReasonTO, PersonServiceReason, ServiceReason>
		implements PersonServiceReasonTOFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonServiceReasonTOFactoryImpl.class);

	public PersonServiceReasonTOFactoryImpl() {
		super(PersonServiceReasonTO.class,
				PersonServiceReason.class);
	}

	@Autowired
	private transient PersonServiceReasonDao dao;

	@Autowired
	private transient ServiceReasonService service;

	@Autowired
	private transient PersonService personService;

	@Override
	protected PersonServiceReasonDao getDao() {
		return dao;
	}

	@Override
	public PersonServiceReason from(
			final PersonServiceReasonTO tObject)
			throws ObjectNotFoundException {
		final PersonServiceReason model = super.from(tObject);

		model.setServiceReason(tObject.getServiceReasonId() == null ? null
				: service.get(tObject.getServiceReasonId()));

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		return model;
	}

	@Override
	public PersonServiceReason fromLite(
			final ReferenceLiteTO<ServiceReason> lite,
			final Person person) throws ObjectNotFoundException {

		final PagingWrapper<PersonServiceReason> results = dao
				.getAllForPersonIdAndServiceReasonId(person.getId(),
						lite.getId(),
						new SortingAndPaging(ObjectStatus.ACTIVE));

		if (results.getResults() > 1) {
			LOGGER.error("Multiple active PersonServiceReasons found for Person: "
					+ person.getId().toString()
					+ ", ServiceReason: "
					+ lite.getId().toString());
			return results.getRows().iterator().next();
		} else if (results.getResults() == 1) {
			return results.getRows().iterator().next();
		}

		// else
		final PersonServiceReason pssg = new PersonServiceReason();
		pssg.setPerson(person);
		pssg.setServiceReason(service.get(lite.getId()));
		return pssg;
	}
}