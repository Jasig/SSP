package edu.sinclair.ssp.factory.reference.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.factory.reference.EducationGoalTOFactory;
import edu.sinclair.ssp.model.reference.EducationGoal;
import edu.sinclair.ssp.transferobject.reference.EducationGoalTO;

@Service
public class EducationGoalTOFactoryImpl implements EducationGoalTOFactory{

	@Override
	public EducationGoalTO toTO(EducationGoal from) {
		
		EducationGoalTO to = new EducationGoalTO();
		
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
	public EducationGoal toModel(EducationGoalTO from) {
		EducationGoal model = new EducationGoal();
		
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
	public List<EducationGoalTO> toTOList(List<EducationGoal> from) {
		List<EducationGoalTO> toList = Lists.newArrayList();
		for(EducationGoal c : from){
			toList.add(toTO(c));
		}
		return toList;
	}

	@Override
	public List<EducationGoal> toModelList(List<EducationGoalTO> from) {
		List<EducationGoal> toList = Lists.newArrayList();
		for(EducationGoalTO c : from){
			toList.add(toModel(c));
		}
		return toList;
	}

}
