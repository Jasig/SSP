/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
