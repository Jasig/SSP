package org.jasig.ssp.service.reference.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.SelfHelpGuideDao;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideGroup;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.SelfHelpGuideService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
public class SelfHelpGuideServiceImpl extends
		AbstractReferenceService<SelfHelpGuide>
		implements SelfHelpGuideService {

	public SelfHelpGuideServiceImpl() {
		super(SelfHelpGuide.class);
	}

	public SelfHelpGuideServiceImpl(SelfHelpGuideDao dao,
			SecurityService securityService) {
		super(SelfHelpGuide.class);
		this.dao = dao;
		this.securityService = securityService;
	}

	@Autowired
	transient private SelfHelpGuideDao dao;

	@Autowired
	private SecurityService securityService;

	@Override
	public PagingWrapper<SelfHelpGuide> getAll(SortingAndPaging sAndP) {
		if (!securityService.isAuthenticated()) {
			return new PagingWrapper<SelfHelpGuide>(
					dao.findAllActiveForUnauthenticated());
		} else {
			return dao.getAll(sAndP);
		}
	}

	@Override
	public List<SelfHelpGuide> getBySelfHelpGuideGroup(
			SelfHelpGuideGroup selfHelpGuideGroup) {
		return dao
				.findAllActiveBySelfHelpGuideGroup(selfHelpGuideGroup.getId());
	}

	@Override
	protected SelfHelpGuideDao getDao() {
		return dao;
	}

}
