package edu.sinclair.ssp.factory.reference.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.factory.reference.VeteranStatusTOFactory;
import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.transferobject.reference.VeteranStatusTO;

@Service
public class VeteranStatusTOFactoryImpl implements VeteranStatusTOFactory{

	@Override
	public VeteranStatusTO toTO(VeteranStatus from) {
		
		VeteranStatusTO to = new VeteranStatusTO();
		
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
	public VeteranStatus toModel(VeteranStatusTO from) {
		VeteranStatus model = new VeteranStatus();
		
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
	public List<VeteranStatusTO> toTOList(List<VeteranStatus> from) {
		List<VeteranStatusTO> toList = Lists.newArrayList();
		for(VeteranStatus c : from){
			toList.add(toTO(c));
		}
		return toList;
	}

	@Override
	public List<VeteranStatus> toModelList(List<VeteranStatusTO> from) {
		List<VeteranStatus> toList = Lists.newArrayList();
		for(VeteranStatusTO c : from){
			toList.add(toModel(c));
		}
		return toList;
	}

}
