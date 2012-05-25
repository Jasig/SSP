package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.EarlyAlertRoutingDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.EarlyAlertRoutingTOFactory;
import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.EarlyAlertReasonService;
import org.jasig.ssp.transferobject.EarlyAlertRoutingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertRouting transfer object factory implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class EarlyAlertRoutingTOFactoryImpl
		extends
		AbstractAuditableTOFactory<EarlyAlertRoutingTO, EarlyAlertRouting>
		implements EarlyAlertRoutingTOFactory {

	@Autowired
	private transient EarlyAlertRoutingDao dao;

	@Autowired
	private transient CampusService campusService;

	@Autowired
	private transient EarlyAlertReasonService earlyAlertReasonService;

	@Autowired
	private transient PersonService personService;

	/**
	 * Construct an instance with the specific classes needed by super class
	 * methods.
	 */
	public EarlyAlertRoutingTOFactoryImpl() {
		super(EarlyAlertRoutingTO.class, EarlyAlertRouting.class);
	}

	@Override
	protected EarlyAlertRoutingDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertRouting from(final EarlyAlertRoutingTO tObject)
			throws ObjectNotFoundException {
		final EarlyAlertRouting model = super.from(tObject);

		model.setGroupName(tObject.getGroupName());
		model.setGroupEmail(tObject.getGroupEmail());
		model.setCampus(campusService.get(tObject.getCampusId()));
		model.setPerson(personService.get(tObject.getPersonId()));
		model.setEarlyAlertReason(earlyAlertReasonService.get(tObject
				.getEarlyAlertReasonId()));

		return model;
	}
}