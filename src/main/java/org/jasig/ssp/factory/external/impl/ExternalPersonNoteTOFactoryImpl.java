package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalPersonNoteDao;
import org.jasig.ssp.factory.external.ExternalPersonNoteTOFactory;
import org.jasig.ssp.model.external.ExternalPersonNote;
import org.jasig.ssp.transferobject.external.ExternalPersonNoteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExternalPersonNoteTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalPersonNoteTO, ExternalPersonNote>
		implements ExternalPersonNoteTOFactory {

	
	@Autowired
	private transient ExternalPersonNoteDao dao;
	
	public ExternalPersonNoteTOFactoryImpl() {
		super(ExternalPersonNoteTO.class, ExternalPersonNote.class);
	}
	
	@Override
	protected ExternalDataDao<ExternalPersonNote> getDao() {
		return dao;
	}
	
	@Override
	public ExternalPersonNoteTO from(ExternalPersonNote tObject) {
		final ExternalPersonNoteTO model = super.from(tObject);
		model.setAuthor(tObject.getAuthor());
		model.setCode(tObject.getCode());
		model.setDate(tObject.getDate());
		model.setDepartment(tObject.getDepartment());
		model.setNote(tObject.getNote());
		model.setNoteType(tObject.getNoteType());
		model.setSchoolId(tObject.getSchoolId());
		return model;
	}
}
