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
package org.jasig.ssp.dao;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.StudentDocument;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDocumentDao
		extends AbstractRestrictedPersonAssocAuditableCrudDao<StudentDocument>
		implements PersonAssocAuditableCrudDao<StudentDocument> {
 
	public StudentDocumentDao() {
		super(StudentDocument.class);
	}
	
	protected StudentDocumentDao(Class<StudentDocument> persistentClass) {
		super(persistentClass);
	}
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<StudentDocument> getAllForPerson(Person person,
			SortingAndPaging sAndP,final SspUser requestor) {
		Criteria criteria = createCriteria().add(Restrictions.eq("person", person))
						.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
		addConfidentialityLevelsRestriction(requestor, criteria);
		Collection<StudentDocument> rows = criteria.list();
		return new PagingWrapper<StudentDocument>(rows);
	}

}
