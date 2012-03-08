package edu.sinclair.ssp.factory.reference.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.factory.reference.EducationLevelTOFactory;
import edu.sinclair.ssp.model.reference.EducationLevel;
import edu.sinclair.ssp.transferobject.reference.EducationLevelTO;

@Service
public class EducationLevelTOFactoryImpl implements EducationLevelTOFactory{

	@Override
	public EducationLevelTO toTO(EducationLevel from) {
		
		EducationLevelTO to = new EducationLevelTO();
		
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
	public EducationLevel toModel(EducationLevelTO from) {
		EducationLevel model = new EducationLevel();
		
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
	public List<EducationLevelTO> toTOList(List<EducationLevel> from) {
		List<EducationLevelTO> toList = Lists.newArrayList();
		for(EducationLevel c : from){
			toList.add(toTO(c));
		}
		return toList;
	}

	@Override
	public List<EducationLevel> toModelList(List<EducationLevelTO> from) {
		List<EducationLevel> toList = Lists.newArrayList();
		for(EducationLevelTO c : from){
			toList.add(toModel(c));
		}
		return toList;
	}

}
