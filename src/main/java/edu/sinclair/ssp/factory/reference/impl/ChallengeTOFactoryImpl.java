package edu.sinclair.ssp.factory.reference.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.factory.reference.ChallengeTOFactory;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.transferobject.reference.ChallengeTO;

@Service
public class ChallengeTOFactoryImpl implements ChallengeTOFactory{

	@Override
	public ChallengeTO toTO(Challenge from) {
		
		ChallengeTO to = new ChallengeTO();
		
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
	public Challenge toModel(ChallengeTO from) {
		Challenge model = new Challenge();
		
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
	public List<ChallengeTO> toTOList(List<Challenge> from) {
		List<ChallengeTO> toList = Lists.newArrayList();
		for(Challenge c : from){
			toList.add(toTO(c));
		}
		return toList;
	}

	@Override
	public List<Challenge> toModelList(List<ChallengeTO> from) {
		List<Challenge> toList = Lists.newArrayList();
		for(ChallengeTO c : from){
			toList.add(toModel(c));
		}
		return toList;
	}

}
