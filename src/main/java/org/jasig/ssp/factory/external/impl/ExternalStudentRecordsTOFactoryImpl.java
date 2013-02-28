package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.factory.external.ExternalStudentRecordsTOFactory;
import org.jasig.ssp.model.external.ExternalStudentRecords;
import org.jasig.ssp.model.external.ExternalStudentRecordsLite;
import org.jasig.ssp.transferobject.external.ExternalStudentRecordsLiteTO;
import org.jasig.ssp.transferobject.external.ExternalStudentRecordsTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class ExternalStudentRecordsTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalStudentRecordsTO, ExternalStudentRecords>
implements ExternalStudentRecordsTOFactory{

	
	public ExternalStudentRecordsTOFactoryImpl() {
		super(ExternalStudentRecordsTO.class, ExternalStudentRecords.class);
	}
	
	
	public ExternalStudentRecordsTOFactoryImpl(
			Class<ExternalStudentRecordsTO> toClass,
			Class<ExternalStudentRecords> mClass) {
		super(toClass, mClass);
	}

	@Override
	protected ExternalDataDao<ExternalStudentRecords> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
