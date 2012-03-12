package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.ChildCareArrangementDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.SecurityService;
import edu.sinclair.ssp.service.reference.ChildCareArrangementService;

@Service
@Transactional
public class ChildCareArrangementServiceImpl implements ChildCareArrangementService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ChildCareArrangementServiceImpl.class);

	@Autowired
	private ChildCareArrangementDao dao;
	
	@Autowired
	private SecurityService securityService;

	@Override
	public List<ChildCareArrangement> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public ChildCareArrangement get(UUID id) throws ObjectNotFoundException {
		ChildCareArrangement obj = dao.get(id);
		if(null==obj){
			throw new ObjectNotFoundException(id, "ChildCareArrangement");
		}
		return obj;
	}

	@Override
	public ChildCareArrangement create(ChildCareArrangement obj) {
		obj.setRequiredOnCreate(
				securityService.currentlyLoggedInSspUser().getPerson());
		return dao.save(obj);
	}

	@Override
	public ChildCareArrangement save(ChildCareArrangement obj) throws ObjectNotFoundException {
		ChildCareArrangement current = get(obj.getId());
		
		current.setRequiredOnModify(
				securityService.currentlyLoggedInSspUser().getPerson());
		
		if(obj.getName()!=null){
			current.setName(obj.getName());
		}
		if(obj.getDescription()!=null){
			current.setDescription(obj.getDescription());
		}
		if(obj.getObjectStatus()!=null){
			current.setObjectStatus(obj.getObjectStatus());
		}
		
		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException{
		ChildCareArrangement current = get(id);
		
		if(null!=current){
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(ChildCareArrangementDao dao){
		this.dao = dao;
	}

	protected void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
