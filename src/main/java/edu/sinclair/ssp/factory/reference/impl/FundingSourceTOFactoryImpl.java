package edu.sinclair.ssp.factory.reference.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.factory.reference.FundingSourceTOFactory;
import edu.sinclair.ssp.model.reference.FundingSource;
import edu.sinclair.ssp.transferobject.reference.FundingSourceTO;

@Service
public class FundingSourceTOFactoryImpl implements FundingSourceTOFactory{

	@Override
	public FundingSourceTO toTO(FundingSource from) {
		
		FundingSourceTO to = new FundingSourceTO();
		
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
	public FundingSource toModel(FundingSourceTO from) {
		FundingSource model = new FundingSource();
		
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
	public List<FundingSourceTO> toTOList(List<FundingSource> from) {
		List<FundingSourceTO> toList = Lists.newArrayList();
		for(FundingSource c : from){
			toList.add(toTO(c));
		}
		return toList;
	}

	@Override
	public List<FundingSource> toModelList(List<FundingSourceTO> from) {
		List<FundingSource> toList = Lists.newArrayList();
		for(FundingSourceTO c : from){
			toList.add(toModel(c));
		}
		return toList;
	}

}
