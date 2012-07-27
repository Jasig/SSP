package org.jasig.ssp.service.impl;

import java.util.UUID;

import org.jasig.ssp.dao.PersonDemographicsDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonDemographicsService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonDemographicsServiceImpl implements PersonDemographicsService {

	@Autowired
	private transient PersonDemographicsDao dao;

	@Override
	public PagingWrapper<PersonDemographics> getAll(final SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public PersonDemographics get(final UUID id) throws ObjectNotFoundException {
		return dao.get(id);
	}

	@Override
	public PersonDemographics forPerson(final Person person) {
		return person.getDemographics();
	}

	@Override
	public PersonDemographics create(final PersonDemographics obj) {
		return dao.save(obj);
	}

	@Override
	public PersonDemographics save(final PersonDemographics obj)
			throws ObjectNotFoundException {
		return dao.save(obj);
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final PersonDemographics current = get(id);

		if ((null != current)
				&& !ObjectStatus.INACTIVE.equals(current.getObjectStatus())) {
			current.setObjectStatus(ObjectStatus.INACTIVE);
			save(current);
		}
	}
}