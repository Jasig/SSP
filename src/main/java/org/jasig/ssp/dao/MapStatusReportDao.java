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

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jasig.ssp.model.MapStatusReport;
import org.springframework.stereotype.Repository;

@Repository
public class MapStatusReportDao  extends AbstractPersonAssocAuditableCrudDao<MapStatusReport> implements PersonAssocAuditableCrudDao<MapStatusReport> { 



	public MapStatusReportDao() {
		super(MapStatusReport.class);
	}
	protected MapStatusReportDao(Class<MapStatusReport> persistentClass) {
		super(MapStatusReport.class);
	}

	public void deleteAllOldReports() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String deleteCourseDetailsHql = "delete org.jasig.ssp.model.MapStatusReportCourseDetails msrcd";
		session.createQuery(deleteCourseDetailsHql).executeUpdate();
		String deleteTermDetailsHql = "delete org.jasig.ssp.model.MapStatusReportTermDetails msrtd";
		session.createQuery(deleteTermDetailsHql).executeUpdate();
		String deleteMapStatusReportHql = "delete org.jasig.ssp.model.MapStatusReport msrtd";
		session.createQuery(deleteMapStatusReportHql).executeUpdate();

		
		tx.commit();
		session.close();

	}


}
