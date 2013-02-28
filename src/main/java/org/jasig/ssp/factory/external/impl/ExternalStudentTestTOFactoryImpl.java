package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.factory.external.ExternalStudentTestTOFactory;
import org.jasig.ssp.model.external.ExternalStudentTest;
import org.jasig.ssp.transferobject.external.ExternalStudentTestTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class ExternalStudentTestTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalStudentTestTO, ExternalStudentTest> implements
		ExternalStudentTestTOFactory {
	
	public ExternalStudentTestTOFactoryImpl(){
		super(ExternalStudentTestTO.class, ExternalStudentTest.class);
	}

	public ExternalStudentTestTOFactoryImpl(
			Class<ExternalStudentTestTO> toClass,
			Class<ExternalStudentTest> mClass) {
		super(toClass, mClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ExternalDataDao<ExternalStudentTest> getDao() {
		// TODO Auto-generated method stub
		return null;
	}



}
