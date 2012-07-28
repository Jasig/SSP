package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalFacultyCourseRosterDao;
import org.jasig.ssp.factory.external.ExternalFacultyCourseRosterTOFactory;
import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalPersonLiteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FacultyCourse transfer object factory implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class ExternalFacultyCourseRosterTOFactoryImpl
		extends
		AbstractExternalDataTOFactory<ExternalPersonLiteTO, ExternalFacultyCourseRoster>
		implements ExternalFacultyCourseRosterTOFactory {

	public ExternalFacultyCourseRosterTOFactoryImpl() {
		super(ExternalPersonLiteTO.class, ExternalFacultyCourseRoster.class);
	}

	@Autowired
	private transient ExternalFacultyCourseRosterDao dao;

	@Override
	protected ExternalFacultyCourseRosterDao getDao() {
		return dao;
	}

	@Override
	public ExternalFacultyCourseRoster from(final ExternalPersonLiteTO tObject)
			throws ObjectNotFoundException {
		final ExternalFacultyCourseRoster model = super.from(tObject);

		model.setSchoolId(tObject.getSchoolId());
		model.setFirstName(tObject.getFirstName());
		model.setMiddleName(tObject.getMiddleName());
		model.setLastName(tObject.getLastName());

		return model;
	}
}