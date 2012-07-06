package org.jasig.ssp.service.impl;

import org.jasig.ssp.dao.CaseloadDao;
import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.CaseloadService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CaseloadServiceImpl implements CaseloadService {

	@Autowired
	private transient ProgramStatusService programStatusService;

	@Autowired
	private transient CaseloadDao dao;

	@Override
	public PagingWrapper<CaseloadRecord> caseLoadFor(
			final ProgramStatus programStatus,
			final Person coach, final SortingAndPaging sAndP)
			throws ObjectNotFoundException {

		// programStatus : <programStatusId>, default to Active
		if (programStatus == null) {
			programStatusService.get(ProgramStatus.ACTIVE_ID);
		}

		return dao.caseLoadFor(programStatus, coach, sAndP);
	}

}
