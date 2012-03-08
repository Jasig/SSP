package edu.sinclair.ssp.factory.reference.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.factory.reference.StudentStatusTOFactory;
import edu.sinclair.ssp.model.reference.StudentStatus;
import edu.sinclair.ssp.transferobject.reference.StudentStatusTO;

@Service
public class StudentStatusTOFactoryImpl implements StudentStatusTOFactory{

	@Override
	public StudentStatusTO toTO(StudentStatus from) {
		
		StudentStatusTO to = new StudentStatusTO();
		
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
	public StudentStatus toModel(StudentStatusTO from) {
		StudentStatus model = new StudentStatus();
		
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
	public List<StudentStatusTO> toTOList(List<StudentStatus> from) {
		List<StudentStatusTO> toList = Lists.newArrayList();
		for(StudentStatus c : from){
			toList.add(toTO(c));
		}
		return toList;
	}

	@Override
	public List<StudentStatus> toModelList(List<StudentStatusTO> from) {
		List<StudentStatus> toList = Lists.newArrayList();
		for(StudentStatusTO c : from){
			toList.add(toModel(c));
		}
		return toList;
	}

}
