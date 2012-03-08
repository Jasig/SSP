package edu.sinclair.ssp.service.reference.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.EthnicityDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.SecurityService;
import edu.sinclair.ssp.service.reference.EthnicityService;
import edu.sinclair.ssp.service.reference.ReferenceService;

@Service
@Transactional
public class EthnicityServiceImpl implements ReferenceService<Ethnicity>, EthnicityService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(EthnicityServiceImpl.class);

	@Autowired
	private EthnicityDao dao;
	
	@Autowired
	private SecurityService securityService;

	@Override
	public List<Ethnicity> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public Ethnicity get(UUID id) throws ObjectNotFoundException {
		Ethnicity obj = dao.get(id);
		if(null==obj){
			throw new ObjectNotFoundException(id, "Ethnicity");
		}
		return obj;
	}

	@Override
	public Ethnicity create(Ethnicity obj) {
		obj.setCreatedBy(securityService.currentlyLoggedInSspUser().getPerson());
		obj.setCreatedDate(new Date());
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setModifiedBy(obj.getCreatedBy());
		obj.setModifiedDate(obj.getCreatedDate());
		return dao.save(obj);
	}

	@Override
	public Ethnicity save(Ethnicity obj) throws ObjectNotFoundException {
		Ethnicity current = get(obj.getId());
		
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
		
		return dao.save(obj);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException{
		Ethnicity current = get(id);
		
		if(null!=current){
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(EthnicityDao dao){
		this.dao = dao;
	}

	protected void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
