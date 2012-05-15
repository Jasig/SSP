package org.jasig.ssp.service.reference.impl;

import java.util.List;

import org.jasig.ssp.dao.reference.SelfHelpGuideDao;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideGroup;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.SelfHelpGuideService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SelfHelpGuide implementation service
 */
@Service
@Transactional
public class SelfHelpGuideServiceImpl extends
		AbstractReferenceService<SelfHelpGuide>
		implements SelfHelpGuideService {

	public SelfHelpGuideServiceImpl() {
		super();
	}

	public SelfHelpGuideServiceImpl(final SelfHelpGuideDao dao,
			final SecurityService securityService) {
		super();
		this.dao = dao;
		this.securityService = securityService;
	}

	@Autowired
	private transient SelfHelpGuideDao dao;

	@Autowired
	private transient SecurityService securityService;

	@Override
	public PagingWrapper<SelfHelpGuide> getAll(final SortingAndPaging sAndP) {
		if (securityService.isAuthenticated()) {
			return dao.getAll(sAndP);
		} else {
			return new PagingWrapper<SelfHelpGuide>(
					dao.findAllActiveForUnauthenticated());
		}
	}

	@Override
	public List<SelfHelpGuide> getBySelfHelpGuideGroup(
			final SelfHelpGuideGroup selfHelpGuideGroup) {
		return dao
				.findAllActiveBySelfHelpGuideGroup(selfHelpGuideGroup.getId());
	}

	@Override
	protected SelfHelpGuideDao getDao() {
		return dao;
	}
}
