package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.SelfHelpGuideDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.service.reference.SelfHelpGuideService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
public class SelfHelpGuideServiceImpl implements SelfHelpGuideService {

	@Autowired
	private SelfHelpGuideDao dao;

	@Autowired
	private SecurityService securityService;

	public SelfHelpGuideServiceImpl() {
	}

	public SelfHelpGuideServiceImpl(SelfHelpGuideDao dao,
			SecurityService securityService) {
		this.dao = dao;
		this.securityService = securityService;
	}

	@Override
	public List<SelfHelpGuide> getAll(SortingAndPaging sAndP) {
		if (!securityService.isAuthenticated()) {
			return dao.findAllActiveForUnauthenticated();
		} else {
			return dao.getAll(sAndP);
		}
	}

	@Override
	public SelfHelpGuide get(UUID id) throws ObjectNotFoundException {
		SelfHelpGuide obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "SelfHelpGuide");
		}

		return obj;
	}

	@Override
	public SelfHelpGuide create(SelfHelpGuide obj) {
		return dao.save(obj);
	}

	@Override
	public SelfHelpGuide save(SelfHelpGuide obj) throws ObjectNotFoundException {
		SelfHelpGuide current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		SelfHelpGuide current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	@Override
	public List<SelfHelpGuide> getBySelfHelpGuideGroup(
			SelfHelpGuideGroup selfHelpGuideGroup) {
		return dao
				.findAllActiveBySelfHelpGuideGroup(selfHelpGuideGroup.getId());
	}

}
