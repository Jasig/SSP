package edu.sinclair.ssp.factory.reference.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.factory.reference.ChildCareArrangementTOFactory;
import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.transferobject.reference.ChildCareArrangementTO;

@Service
public class ChildCareArrangementTOFactoryImpl implements ChildCareArrangementTOFactory{

	@Override
	public ChildCareArrangementTO toTO(ChildCareArrangement from) {
		
		ChildCareArrangementTO to = new ChildCareArrangementTO();
		
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
	public ChildCareArrangement toModel(ChildCareArrangementTO from) {
		ChildCareArrangement model = new ChildCareArrangement();
		
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
	public List<ChildCareArrangementTO> toTOList(List<ChildCareArrangement> from) {
		List<ChildCareArrangementTO> toList = Lists.newArrayList();
		for(ChildCareArrangement c : from){
			toList.add(toTO(c));
		}
		return toList;
	}

	@Override
	public List<ChildCareArrangement> toModelList(List<ChildCareArrangementTO> from) {
		List<ChildCareArrangement> toList = Lists.newArrayList();
		for(ChildCareArrangementTO c : from){
			toList.add(toModel(c));
		}
		return toList;
	}

}
