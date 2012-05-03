package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.reference.JournalTrackDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.JournalTrackTOFactory;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.JournalTrackTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class JournalTrackTOFactoryImpl extends
		AbstractReferenceTOFactory<JournalTrackTO, JournalTrack>
		implements JournalTrackTOFactory {

	public JournalTrackTOFactoryImpl() {
		super(JournalTrackTO.class, JournalTrack.class);
	}

	@Autowired
	private transient JournalTrackDao dao;

	@Override
	protected JournalTrackDao getDao() {
		return dao;
	}

	@Override
	public JournalTrack from(final JournalTrackTO tObject)
			throws ObjectNotFoundException {
		final JournalTrack model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());

		return model;
	}

}
