package edu.sinclair.ssp.service.reference.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.MaritalStatusDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.SecurityService;
import edu.sinclair.ssp.service.reference.MaritalStatusService;
import edu.sinclair.ssp.service.reference.ReferenceService;

@Service
@Transactional
public class MaritalStatusServiceImpl implements ReferenceService<MaritalStatus>, MaritalStatusService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MaritalStatusServiceImpl.class);

	@Autowired
	private MaritalStatusDao dao;
	
	@Autowired
	private SecurityService securityService;

	@Override
	public List<MaritalStatus> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public MaritalStatus get(UUID id) throws ObjectNotFoundException {
		MaritalStatus obj = dao.get(id);
		if(null==obj){
			throw new ObjectNotFoundException(id, "MaritalStatus");
		}
		return obj;
	}

	@Override
	public MaritalStatus create(MaritalStatus obj) {
		obj.setCreatedBy(securityService.currentlyLoggedInSspUser().getPerson());
		obj.setCreatedDate(new Date());
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setModifiedBy(obj.getCreatedBy());
		obj.setModifiedDate(obj.getCreatedDate());
		return dao.save(obj);
	}

	@Override
	public MaritalStatus save(MaritalStatus obj) throws ObjectNotFoundException {
		MaritalStatus current = get(obj.getId());
		
		current.setModifiedBy(securityService.currentlyLoggedInSspUser().getPerson());
		current.setModifiedDate(new Date());
		
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
		MaritalStatus current = get(id);
		
		if(null!=current){
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(MaritalStatusDao dao){
		this.dao = dao;
	}

	protected void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
