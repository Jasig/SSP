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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.model.AppointmentStartTime;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalStudentFinancialAidAwardTerm;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

/**
 * Appointment DAO
 */
@Repository
public class AppointmentDao
		extends AbstractPersonAssocAuditableCrudDao<Appointment>
		implements PersonAssocAuditableCrudDao<Appointment> {

	protected AppointmentDao() {
		super(Appointment.class);
	}

	public Appointment getCurrentAppointmentForPerson(final Person person) {

		final Criteria query = createCriteria();

		// Active appointment for the given person
		query.add(Restrictions.eq("person", person));
		query.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));

		// only return the most recently modified appointment
		query.addOrder(Order.desc("modifiedDate"));
		query.setMaxResults(1);

		return (Appointment) query.uniqueResult();
	}
	
	public Map<UUID,Date> getCurrentAppointmentDatesForPeopleIds(Collection<UUID> peopleIds){
		List<List<UUID>> batches = prepareBatches(peopleIds);
		String baseQuery = "select appt.id from Appointment as appt "
				+ "where appt.person.id in (%) and appt.objectStatus = 1 group by appt.person.id, appt.id, appt.modifiedDate order by appt.modifiedDate desc";
		Map<UUID, Date> map = new HashMap<UUID, Date>();
		for (List<UUID> batch : batches) 
		{
			StringBuilder inClause = new StringBuilder();
			for (UUID studentId : batch) 
			{
				inClause.append("'");
				inClause.append(studentId.toString());
				inClause.append("'");
				inClause.append(",");
			}
			if(inClause.lastIndexOf(",") < 0)
				break;
			inClause.deleteCharAt(inClause.lastIndexOf(","));
			String query = StringUtils.replace(baseQuery, "%", inClause.toString());
			List<UUID> list = createHqlQuery( query ).setMaxResults(1).list();
			List<AppointmentStartTime> appointmentDates = new ArrayList<AppointmentStartTime>();
			/* because postgres does not support limit inside of select clauses **/
			if(list.size()  > 0){
				String secondQuery = "select appt.person.id as appt_personId, appt.startTime as appt_startTime from  Appointment as appt "
				+ "where appt.id in :appointments";
				appointmentDates = createHqlQuery( secondQuery ).setParameterList("appointments", list).
						setResultTransformer(new NamespacedAliasToBeanResultTransformer(
						AppointmentStartTime.class, "appt_")).list();
				
				for(AppointmentStartTime st:appointmentDates){
					map.put(st.getPersonId(), st.getStartTime());
				}
			}
			
		}
		return map;
	}
	
	private List<List<UUID>> prepareBatches(Collection<UUID> uuids){
		List<UUID> currentBatch = new ArrayList<UUID>(); 
		List<List<UUID>> batches = new ArrayList<List<UUID>>();
		int batchCounter = 0;
		for (UUID uuid : uuids) 
		{
			if(batchCounter == getBatchsize())
			{
				currentBatch.add(uuid);
				batches.add(currentBatch);
				currentBatch = new ArrayList<UUID>();
				batchCounter = 0;
			}
			else
			{
				currentBatch.add(uuid);
				batchCounter++;
			}
		}
		batches.add(currentBatch);
		return batches;
	}
}
