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

import org.jasig.ssp.dao.DirectoryPersonSearchDao;
import org.jasig.ssp.dao.WatchStudentDao;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * WatchStudent service implementation
 */
@Service
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
	@Transactional
	public WatchStudent get(UUID watcherId, UUID studentId) {
		return dao.getStudentWatcherRelationShip(watcherId,studentId);
	}

	@Override
	@Transactional
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
	@Transactional
	public void delete(final UUID id) throws ObjectNotFoundException {
		final WatchStudent current = getDao().get(id);

		dao.delete(current);
	}

	@Override
	// explicitly leaving out @Transactional. We really shouldn't need it on most of the
	// methods in this class (or at most readOnly), but it's been there historically
	// on it and most of its peers. This export scenario is a special case, though, where
	// we really don't want to be holding open a transaction if we can help it.
	public void exportWatchListFor(PrintWriter writer,
			ProgramStatus programStatus, Person person,
			SortingAndPaging buildSortAndPage) throws IOException {
		PersonSearchRequest form = new PersonSearchRequest();
		form.setWatcher(person);
		form.setMyWatchList(true);
		form.setProgramStatus(programStatus);
		form.setSortAndPage(buildSortAndPage);
		directoryPersonDao.exportableSearch(new CaseloadCsvWriterHelper(writer), form);
	}

	@Override
	@Transactional
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