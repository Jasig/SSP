package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.factory.external.ExternalStudentRecordsLiteTOFactory;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.model.external.ExternalStudentRecordsLite;
import org.jasig.ssp.transferobject.external.ExternalPersonLiteTO;
import org.jasig.ssp.transferobject.external.ExternalStudentRecordsLiteTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExternalStudentRecordsLiteTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalStudentRecordsLiteTO, ExternalStudentRecordsLite> implements
		ExternalStudentRecordsLiteTOFactory {

	
	public ExternalStudentRecordsLiteTOFactoryImpl() {
		super(ExternalStudentRecordsLiteTO.class, ExternalStudentRecordsLite.class);
	}
	
	
	public ExternalStudentRecordsLiteTOFactoryImpl(
			Class<ExternalStudentRecordsLiteTO> toClass,
			Class<ExternalStudentRecordsLite> mClass) {
		super(toClass, mClass);
	}

	@Override
	protected ExternalDataDao<ExternalStudentRecordsLite> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
