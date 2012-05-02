package org.studentsuccessplan.ssp.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.PersonFundingSourceDao;
import org.studentsuccessplan.ssp.factory.AbstractAuditableTOFactory;
import org.studentsuccessplan.ssp.factory.PersonFundingSourceTOFactory;
import org.studentsuccessplan.ssp.model.PersonFundingSource;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.reference.FundingSourceService;
import org.studentsuccessplan.ssp.transferobject.PersonFundingSourceTO;

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
