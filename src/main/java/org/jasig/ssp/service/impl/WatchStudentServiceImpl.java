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

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.jasig.ssp.dao.DirectoryPersonSearchDao;
import org.jasig.ssp.dao.PersonSearchDao;
import org.jasig.ssp.dao.WatchStudentDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.WatchStudent;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.WatchStudentService;
import org.jasig.ssp.util.csvwriter.CaseloadCsvWriterHelper;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * WatchStudent service implementation
 */
@Service
@Transactional
public class WatchStudentServiceImpl
		extends AbstractPersonAssocAuditableService<WatchStudent>
		implements WatchStudentService {

	@Autowired
	transient private WatchStudentDao dao;
	@Autowired
	transient private DirectoryPersonSearchDao directoryPersonDao;

	@Override
	protected WatchStudentDao getDao() {
		return dao;
	}

	@Override
	public WatchStudent get(UUID watcherId, UUID studentId) {
		return dao.getStudentWatcherRelationShip(watcherId,studentId);
	}

	@Override
	public PagingWrapper<PersonSearchResult2> watchListFor(ProgramStatus programStatus, Person person,
			SortingAndPaging sAndP) {
		PersonSearchRequest form = new PersonSearchRequest();
		form.setWatcher(person);
		form.setMyWatchList(true);
		form.setProgramStatus(programStatus);
		form.setSortAndPage(sAndP);
		return directoryPersonDao.search(form);
	}
	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final WatchStudent current = getDao().get(id);

		dao.delete(current);
	}

	@Override
	public void exportWatchListFor(HttpServletResponse response,
			ProgramStatus programStatus, Person person,
			SortingAndPaging buildSortAndPage) throws IOException {
		PersonSearchRequest form = new PersonSearchRequest();
		form.setWatcher(person);
		form.setMyWatchList(true);
		form.setProgramStatus(programStatus);
		form.setSortAndPage(buildSortAndPage);
		directoryPersonDao.exportableSearch(new CaseloadCsvWriterHelper(response), form);
	}

	@Override
	public Long watchListCountFor(ProgramStatus programStatus, Person person,
			SortingAndPaging buildSortAndPage) {
		PersonSearchRequest form = new PersonSearchRequest();
		form.setWatcher(person);
		form.setMyWatchList(true);
		form.setProgramStatus(programStatus);
		form.setSortAndPage(buildSortAndPage);
		return directoryPersonDao.getCaseloadCountFor(form, buildSortAndPage);
	}


}