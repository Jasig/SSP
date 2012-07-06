package org.jasig.ssp.service;

import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface CaseloadService {

	PagingWrapper<CaseloadRecord> caseLoadFor(ProgramStatus programStatus,
			Person coach, SortingAndPaging sAndP)
			throws ObjectNotFoundException;
}
