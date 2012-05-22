package org.jasig.ssp.service;

import org.jasig.ssp.model.Goal;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface GoalService extends PersonAssocService<Goal> {
	@Override
	PagingWrapper<Goal> getAllForPerson(Person person,
			SortingAndPaging sAndP);
}