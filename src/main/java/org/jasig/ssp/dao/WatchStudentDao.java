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

import java.util.List;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.WatchStudent;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author tony.arland
 * 
 */
@Repository
public class WatchStudentDao extends
		AbstractPersonAssocAuditableCrudDao<WatchStudent> implements
		PersonAssocAuditableCrudDao<WatchStudent> {

	/**
	 * Construct a data access instance with specific class types for use by
	 * super class methods.
	 */
	protected WatchStudentDao() {
		super(WatchStudent.class);
	}

	@SuppressWarnings("rawtypes")
	public WatchStudent getStudentWatcherRelationShip(UUID watcherId,
			UUID studentId) {
		String hqlQuery = "from org.jasig.ssp.model.WatchStudent where person = :person and student = :student";
		List result = createHqlQuery(hqlQuery).setEntity("person", new Person(watcherId)).setEntity("student", new Person(studentId)).list();
		if(result.isEmpty())
			return null;
		return (WatchStudent) result.get(0);
	}
	
}