package edu.sinclair.ssp.service.reference.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.VeteranStatusDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.SecurityService;
import edu.sinclair.ssp.service.reference.VeteranStatusService;

@Service
@Transactional
public class VeteranStatusServiceImpl implements AuditableCrudService<VeteranStatus>, VeteranStatusService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(VeteranStatusServiceImpl.class);

	@Autowired
	private VeteranStatusDao dao;
	
	@Autowired
	private SecurityService securityService;

	@Override
	public List<VeteranStatus> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public VeteranStatus get(UUID id) throws ObjectNotFoundException {
		VeteranStatus obj = dao.get(id);
		if(null==obj){
			throw new ObjectNotFoundException(id, "VeteranStatus");
		}
		return obj;
	}

	@Override
	public VeteranStatus create(VeteranStatus obj) {
		obj.setCreatedBy(securityService.currentlyLoggedInSspUser().getPerson());
		obj.setCreatedDate(new Date());
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setModifiedBy(obj.getCreatedBy());
		obj.setModifiedDate(obj.getCreatedDate());
		return dao.save(obj);
	}

	@Override
	public VeteranStatus save(VeteranStatus obj) throws ObjectNotFoundException {
		VeteranStatus current = get(obj.getId());
		
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
		VeteranStatus current = get(id);
		
		if(null!=current){
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(VeteranStatusDao dao){
		this.dao = dao;
	}

	protected void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
