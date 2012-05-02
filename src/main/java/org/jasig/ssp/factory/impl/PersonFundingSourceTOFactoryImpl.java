package org.jasig.ssp.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.PersonFundingSourceDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonFundingSourceTOFactory;
import org.jasig.ssp.model.PersonFundingSource;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.FundingSourceService;
import org.jasig.ssp.transferobject.PersonFundingSourceTO;

@Service
@Transactional(readOnly = true)
public class PersonFundingSourceTOFactoryImpl extends
		AbstractAuditableTOFactory<PersonFundingSourceTO, PersonFundingSource>
		implements PersonFundingSourceTOFactory {

	public PersonFundingSourceTOFactoryImpl() {
		super(PersonFundingSourceTO.class, PersonFundingSource.class);
	}

	@Autowired
	private transient PersonFundingSourceDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient FundingSourceService fundingSourceService;

	@Override
	protected PersonFundingSourceDao getDao() {
		return dao;
	}

	@Override
	public PersonFundingSource from(final PersonFundingSourceTO tObject)
			throws ObjectNotFoundException {
		final PersonFundingSource model = super.from(tObject);

		model.setDescription(tObject.getDescription());

		model.setFundingSource((tObject.getFundingSourceId() == null) ? null :
				fundingSourceService.get(tObject.getFundingSourceId()));

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		return model;
	}
}
