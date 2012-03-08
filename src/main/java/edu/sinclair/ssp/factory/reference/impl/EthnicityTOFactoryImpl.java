package edu.sinclair.ssp.factory.reference.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.factory.reference.EthnicityTOFactory;
import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.transferobject.reference.EthnicityTO;

@Service
public class EthnicityTOFactoryImpl implements EthnicityTOFactory{

	@Override
	public EthnicityTO toTO(Ethnicity from) {
		
		EthnicityTO to = new EthnicityTO();
		
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
	public Ethnicity toModel(EthnicityTO from) {
		Ethnicity model = new Ethnicity();
		
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
	public List<EthnicityTO> toTOList(List<Ethnicity> from) {
		List<EthnicityTO> toList = Lists.newArrayList();
		for(Ethnicity c : from){
			toList.add(toTO(c));
		}
		return toList;
	}

	@Override
	public List<Ethnicity> toModelList(List<EthnicityTO> from) {
		List<Ethnicity> toList = Lists.newArrayList();
		for(EthnicityTO c : from){
			toList.add(toModel(c));
		}
		return toList;
	}

}
