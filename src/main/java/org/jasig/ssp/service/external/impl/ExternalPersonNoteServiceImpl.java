package org.jasig.ssp.service.external.impl;

import java.util.List;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalDivisionDao;
import org.jasig.ssp.dao.external.ExternalPersonNoteDao;
import org.jasig.ssp.model.external.ExternalPersonNote;
import org.jasig.ssp.service.external.ExternalPersonNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExternalPersonNoteServiceImpl extends
		AbstractExternalReferenceDataService<ExternalPersonNote> implements
		ExternalPersonNoteService {

	@Autowired
	transient private ExternalPersonNoteDao dao;

	@Override
	public List<ExternalPersonNote> getNotesBySchoolId(String schoolId) {
		// TODO Auto-generated method stub
		return dao.getNoteBySchoolId(schoolId);
	}

	@Override
	protected ExternalDataDao<ExternalPersonNote> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
