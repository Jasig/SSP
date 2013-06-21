package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalCourseRequisiteDao;
import org.jasig.ssp.factory.external.ExternalCourseRequisiteTOFactory;
import org.jasig.ssp.model.external.ExternalCourseRequisite;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalCourseRequisiteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExternalCourseRequisiteTOFactoryImpl
		extends
		AbstractExternalDataTOFactory<ExternalCourseRequisiteTO, ExternalCourseRequisite>
		implements ExternalCourseRequisiteTOFactory {

	public ExternalCourseRequisiteTOFactoryImpl() {
		super(ExternalCourseRequisiteTO.class, ExternalCourseRequisite.class);
	}
 
	@Autowired
	private transient ExternalCourseRequisiteDao dao;

	@Override
	protected ExternalCourseRequisiteDao getDao() {
		return dao;
	}
	
	@Override
	public ExternalCourseRequisite from(final ExternalCourseRequisiteTO tObject) throws ObjectNotFoundException {
		final ExternalCourseRequisite model = super.from(tObject);
		model.setRequiredCourseCode(tObject.getRequiredCourseCode());
		model.setRequiringCourseCode(tObject.getRequiringCourseCode());
		model.setRequisiteCode(tObject.getRequisiteCode());
		model.setRequiredFormattedCourse(tObject.getRequiredFormattedCourse());
		return model;
	}
}
