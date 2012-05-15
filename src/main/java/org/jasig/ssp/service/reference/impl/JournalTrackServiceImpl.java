package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.JournalTrackDao;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.service.reference.JournalTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * JournalTrack implementation service
 * 
 * @author daniel.bower
 */
@Service
@Transactional
public class JournalTrackServiceImpl extends
		AbstractReferenceService<JournalTrack>
		implements JournalTrackService {

	@Autowired
	transient private JournalTrackDao dao;

	protected void setDao(final JournalTrackDao dao) {
		this.dao = dao;
	}

	@Override
	protected JournalTrackDao getDao() {
		return dao;
	}
}
