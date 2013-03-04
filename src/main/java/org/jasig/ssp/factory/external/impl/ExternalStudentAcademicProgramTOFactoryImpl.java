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
import org.jasig.ssp.factory.external.ExternalStudentAcademicProgramTOFactory;
import org.jasig.ssp.model.external.ExternalStudentAcademicProgram;
import org.jasig.ssp.transferobject.external.ExternalStudentAcademicProgramTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExternalStudentAcademicProgramTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalStudentAcademicProgramTO, ExternalStudentAcademicProgram> implements
		ExternalStudentAcademicProgramTOFactory {

	public ExternalStudentAcademicProgramTOFactoryImpl(
			Class<ExternalStudentAcademicProgramTO> toClass,
			Class<ExternalStudentAcademicProgram> mClass) {
		super(toClass, mClass);
		// TODO Auto-generated constructor stub
	}
	
	public ExternalStudentAcademicProgramTOFactoryImpl(){
		super(ExternalStudentAcademicProgramTO.class, ExternalStudentAcademicProgram.class);
	}

	@Override
	protected ExternalDataDao<ExternalStudentAcademicProgram> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
