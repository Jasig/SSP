package edu.sinclair.ssp.factory.reference.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.factory.reference.CitizenshipTOFactory;
import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.transferobject.reference.CitizenshipTO;

@Service
public class CitizenshipTOFactoryImpl implements CitizenshipTOFactory{

	@Override
	public CitizenshipTO toTO(Citizenship from) {
		
		CitizenshipTO to = new CitizenshipTO();
		
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
	public Citizenship toModel(CitizenshipTO from) {
		Citizenship model = new Citizenship();
		
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
	public List<CitizenshipTO> toTOList(List<Citizenship> from) {
		List<CitizenshipTO> toList = Lists.newArrayList();
		for(Citizenship c : from){
			toList.add(toTO(c));
		}
		return toList;
	}

	@Override
	public List<Citizenship> toModelList(List<CitizenshipTO> from) {
		List<Citizenship> toList = Lists.newArrayList();
		for(CitizenshipTO c : from){
			toList.add(toModel(c));
		}
		return toList;
	}

}
