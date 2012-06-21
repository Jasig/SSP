package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.PersonProgramStatusDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonProgramStatusTOFactory;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ProgramStatusChangeReasonService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.PersonProgramStatusTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PersonProgramStatus transfer object factory implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional(readOnly = true)
public class PersonProgramStatusTOFactoryImpl
		extends
		AbstractAuditableTOFactory<PersonProgramStatusTO, PersonProgramStatus>
		implements PersonProgramStatusTOFactory {

	public PersonProgramStatusTOFactoryImpl() {
		super(PersonProgramStatusTO.class, PersonProgramStatus.class);
	}

	@Autowired
	private transient PersonProgramStatusDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient ProgramStatusService service;

	@Autowired
	private transient ProgramStatusChangeReasonService serviceProgramStatusChangeReasonService;

	@Override
	protected PersonProgramStatusDao getDao() {
		return dao;
	}

	@Override
	public PersonProgramStatus from(
			final PersonProgramStatusTO tObject)
			throws ObjectNotFoundException {
		final PersonProgramStatus model = super.from(tObject);

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		model.setProgramStatus(tObject.getProgramStatusId() == null ? null
				: service.get(tObject.getProgramStatusId()));
		model.setProgramStatusChangeReason(tObject
				.getProgramStatusChangeReasonId() == null ? null
				: serviceProgramStatusChangeReasonService.get(tObject
						.getProgramStatusChangeReasonId()));

		model.setEffectiveDate(tObject.getEffectiveDate());
		model.setExpirationDate(tObject.getExpirationDate());

		return model;
	}
}