package org.jasig.ssp.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.dao.AppointmentDao;
import org.jasig.ssp.dao.CaseloadDao;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
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
	private transient AppointmentDao appointmentDao;

	@Autowired
	private transient EarlyAlertService earlyAlertService;

	@Override
	public PagingWrapper<CaseloadRecord> caseLoadFor(
			final ProgramStatus programStatus,
			final Person coach, final SortingAndPaging sAndP)
			throws ObjectNotFoundException {

		// programStatus : <programStatusId>, default to Active
		if (programStatus == null) {
			programStatusService.get(ProgramStatus.ACTIVE_ID);
		}

		final PagingWrapper<CaseloadRecord> records = dao.caseLoadFor(
				programStatus, coach, sAndP);

		final List<UUID> peopleIds = Lists.newArrayList();
		for (CaseloadRecord record : records) {
			peopleIds.add(record.getPersonId());
		}

		final Map<UUID, Appointment> appts = appointmentDao
				.getCurrentAppointmentForPeopleIds(peopleIds);

		final Map<UUID, Number> earlyAlertCounts = earlyAlertService
				.getCountOfActiveAlertsForPeopleIds(peopleIds);

		for (CaseloadRecord record : records) {
			if (appts.containsKey(record.getPersonId())) {
				record.setCurrentAppointmentDate(appts
						.get(record.getPersonId())
						.getStartTime());
			}
			if (earlyAlertCounts.containsKey(record.getPersonId())) {
				record.setNumberOfEarlyAlerts(earlyAlertCounts.get(record
						.getPersonId()));
			}
		}

		return records;
	}

}
