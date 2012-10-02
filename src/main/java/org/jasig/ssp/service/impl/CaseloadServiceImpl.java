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
package org.jasig.ssp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.CaseloadDao;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.AppointmentService;
import org.jasig.ssp.service.CaseloadService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional
public class CaseloadServiceImpl implements CaseloadService {

	@Autowired
	private transient ProgramStatusService programStatusService;

	@Autowired
	private transient CaseloadDao dao;

	@Autowired
	private transient AppointmentService appointmentService;

	@Autowired
	private transient EarlyAlertService earlyAlertService;

	@Override
	public PagingWrapper<CaseloadRecord> caseLoadFor(
			final ProgramStatus programStatus, @NotNull final Person coach,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {

		ProgramStatus programStatusOrDefault;

		// programStatus : <programStatusId>, default to Active
		if (programStatus == null) {
			programStatusOrDefault = programStatusService
					.get(ProgramStatus.ACTIVE_ID);
		} else {
			programStatusOrDefault = programStatus;
		}

		final PagingWrapper<CaseloadRecord> records = dao.caseLoadFor(
				programStatusOrDefault, coach, sAndP);

		final List<UUID> peopleIds = Lists.newArrayList();
		for (final CaseloadRecord record : records) {
			peopleIds.add(record.getPersonId());
		}

		final Map<UUID, Appointment> appts = appointmentService
				.getCurrentAppointmentForPeopleIds(peopleIds);

		final Map<UUID, Number> earlyAlertCounts = earlyAlertService
				.getCountOfActiveAlertsForPeopleIds(peopleIds);

		for (final CaseloadRecord record : records) {
			if (appts.containsKey(record.getPersonId())) {
				record.setCurrentAppointmentStartTime(appts.get(
						record.getPersonId()).getStartTime());
			}

			if (earlyAlertCounts.containsKey(record.getPersonId())) {
				record.setNumberOfEarlyAlerts(earlyAlertCounts.get(record
						.getPersonId()));
			}
		}

		return records;
	}
	
	
	@Override
	public Long caseLoadCountFor(
			final ProgramStatus programStatus, @NotNull final Person coach, List<UUID> studentTypeIds,Date programStatusDateFrom, Date programStatusDateTo 
			) throws ObjectNotFoundException  {

		ProgramStatus programStatusOrDefault;

		// programStatus : <programStatusId>, default to Active
		if (programStatus == null) {
			programStatusOrDefault = programStatusService
					.get(ProgramStatus.ACTIVE_ID);
		} else {
			programStatusOrDefault = programStatus;
		}

		return dao.caseLoadCountFor(programStatusOrDefault, coach, studentTypeIds, programStatusDateFrom, programStatusDateTo);	}	
}