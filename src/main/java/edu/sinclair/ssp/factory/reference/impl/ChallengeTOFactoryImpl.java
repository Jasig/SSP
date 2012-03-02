package edu.sinclair.ssp.factory.reference.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.factory.reference.ChallengeTOFactory;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.service.PersonService;
import edu.sinclair.ssp.service.reference.ChallengeService;
import edu.sinclair.ssp.transferobject.reference.ChallengeTO;

@Service
@Transactional(readOnly = true)
public class ChallengeTOFactoryImpl implements ChallengeTOFactory{
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private ChallengeService challengeService;

	@Override
	public ChallengeTO toTO(Challenge from) {
		
		ChallengeTO to = new ChallengeTO();
		to.setId(from.getId());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		if(from.getCreatedBy()!=null){
			to.setCreatedById(from.getCreatedBy().getId());
		}
		if(from.getModifiedBy()!=null){
			to.setModifiedById(from.getModifiedBy().getId());
		}
		to.setCreatedDate(from.getCreatedDate());
		to.setModifiedDate(from.getModifiedDate());
		to.setObjectStatus(from.getObjectStatus());
		return to;
	}

	@Override
	public Challenge toModel(ChallengeTO from) {
		Challenge model = null;
		
		if(from.getId()!=null){
			model = challengeService.get(from.getId());
			if(model==null){
				model = new Challenge(from.getId());
			}
		}else{
			model = new Challenge();
		}
		
		model.setName(from.getName());
		model.setDescription(from.getDescription());
		model.setCreatedDate(from.getCreatedDate());
		model.setModifiedDate(from.getModifiedDate());
		model.setObjectStatus(from.getObjectStatus());
		
		if(from.getCreatedById()!=null){
			model.setCreatedBy(personService.personFromId(from.getCreatedById()));
		}
		
		if(from.getModifiedById()!=null){
			model.setModifiedBy(personService.personFromId(from.getModifiedById()));
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
		return null;
	}

}
