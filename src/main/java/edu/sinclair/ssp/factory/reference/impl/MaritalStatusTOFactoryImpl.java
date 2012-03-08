package edu.sinclair.ssp.factory.reference.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.factory.reference.MaritalStatusTOFactory;
import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.transferobject.reference.MaritalStatusTO;

@Service
public class MaritalStatusTOFactoryImpl implements MaritalStatusTOFactory{

	@Override
	public MaritalStatusTO toTO(MaritalStatus from) {
		
		MaritalStatusTO to = new MaritalStatusTO();
		
		if(from.getId()!=null){
			to.setId(from.getId());
		}
		if(from.getName()!=null){
			to.setName(from.getName());
		}
		if(from.getDescription()!=null){
			to.setDescription(from.getDescription());
		}
		if(from.getCreatedBy()!=null){
			to.setCreatedById(from.getCreatedBy().getId());
		}
		if(from.getModifiedBy()!=null){
			to.setModifiedById(from.getModifiedBy().getId());
		}
		if(from.getCreatedDate()!=null){
			to.setCreatedDate(from.getCreatedDate());
		}
		if(from.getModifiedDate()!=null){
			to.setModifiedDate(from.getModifiedDate());
		}
		if(from.getObjectStatus()!=null){
			to.setObjectStatus(from.getObjectStatus());
		}
		
		return to;
	}

	@Override
	public MaritalStatus toModel(MaritalStatusTO from) {
		MaritalStatus model = new MaritalStatus();
		
		if(from.getId()!=null){
			model.setId(from.getId());
		}
		
		if(from.getName()!=null){
			model.setName(from.getName());
		}
		if(from.getDescription()!=null){
			model.setDescription(from.getDescription());
		}
		if(from.getObjectStatus()!=null){
			model.setObjectStatus(from.getObjectStatus());
		}
		
		return model;
	}

	@Override
	public List<MaritalStatusTO> toTOList(List<MaritalStatus> from) {
		List<MaritalStatusTO> toList = Lists.newArrayList();
		for(MaritalStatus c : from){
			toList.add(toTO(c));
		}
		return toList;
	}

	@Override
	public List<MaritalStatus> toModelList(List<MaritalStatusTO> from) {
		List<MaritalStatus> toList = Lists.newArrayList();
		for(MaritalStatusTO c : from){
			toList.add(toModel(c));
		}
		return toList;
	}

}
