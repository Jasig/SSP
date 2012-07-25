package org.jasig.ssp.service.impl;

import java.util.UUID;

import org.jasig.ssp.dao.PersonStaffDetailsDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonStaffDetails;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonStaffDetailsService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PersonStaffDetails service implementation
 * 
 * @author jon.adams
 */
@Service
public class PersonStaffDetailsServiceImpl implements PersonStaffDetailsService {

	@Autowired
	private transient PersonStaffDetailsDao dao;

	@Override
	public PagingWrapper<PersonStaffDetails> getAll(final SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public PersonStaffDetails get(final UUID id) throws ObjectNotFoundException {
		return dao.get(id);
	}

	@Override
	public PersonStaffDetails forPerson(final Person person) {
		return person.getStaffDetails();
	}

	@Override
	public PersonStaffDetails create(final PersonStaffDetails obj) {
		return dao.save(obj);
	}

	@Override
	public PersonStaffDetails save(final PersonStaffDetails obj)
			throws ObjectNotFoundException {
		final PersonStaffDetails current = get(obj.getId());

		if (obj.getObjectStatus() != null) {
			current.setObjectStatus(obj.getObjectStatus());
		}

		current.setOfficeLocation(obj.getOfficeLocation());
		current.setOfficeHours(obj.getOfficeHours());
		current.setDepartmentName(obj.getDepartmentName());

		return dao.save(current);
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final PersonStaffDetails current = get(id);

		if (null != current
				&& !ObjectStatus.INACTIVE.equals(current.getObjectStatus())) {
			current.setObjectStatus(ObjectStatus.INACTIVE);
			save(current);
		}
	}
}