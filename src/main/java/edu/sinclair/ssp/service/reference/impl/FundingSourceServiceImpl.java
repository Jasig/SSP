package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.FundingSourceDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.FundingSource;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.FundingSourceService;

@Service
@Transactional
public class FundingSourceServiceImpl implements FundingSourceService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(FundingSourceServiceImpl.class);

	@Autowired
	private FundingSourceDao dao;
	
	@Override
	public List<FundingSource> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public FundingSource get(UUID id) throws ObjectNotFoundException {
		FundingSource obj = dao.get(id);
		if(null==obj){
			throw new ObjectNotFoundException(id, "FundingSource");
		}
		return obj;
	}

	@Override
	public FundingSource create(FundingSource obj) {
		return dao.save(obj);
	}

	@Override
	public FundingSource save(FundingSource obj) throws ObjectNotFoundException {
		FundingSource current = get(obj.getId());
		
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
		FundingSource current = get(id);
		
		if(null!=current){
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(FundingSourceDao dao){
		this.dao = dao;
	}

}
