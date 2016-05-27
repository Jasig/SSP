/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.reference.TemplateElectiveCourseElectiveDetailDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.reference.TemplateElectiveCourseElectiveCourseDetailTOFactory;
import org.jasig.ssp.model.TemplateElectiveCourseElective;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.TemplateElectiveCourseElectiveTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TemplateElectiveCourseElectiveCourseDetailTOFactoryImpl extends
		AbstractAuditableTOFactory<TemplateElectiveCourseElectiveTO, TemplateElectiveCourseElective>
		implements TemplateElectiveCourseElectiveCourseDetailTOFactory {

	public TemplateElectiveCourseElectiveCourseDetailTOFactoryImpl() {
		super(TemplateElectiveCourseElectiveTO.class, TemplateElectiveCourseElective.class);
	}

	@Autowired
	private transient TemplateElectiveCourseElectiveDetailDao dao;

	@Override
	protected TemplateElectiveCourseElectiveDetailDao getDao() {
		return dao;
	}

	@Override
	public TemplateElectiveCourseElective from(final TemplateElectiveCourseElectiveTO tObject)
			throws ObjectNotFoundException {
		final TemplateElectiveCourseElective model = super.from(tObject);

//		model.setSortOrder(tObject.getSortOrder());

		return model;
	}

}
