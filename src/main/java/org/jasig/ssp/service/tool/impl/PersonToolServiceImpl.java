package org.jasig.ssp.service.tool.impl;

import org.jasig.ssp.dao.tool.PersonToolDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.tool.PersonTool;
import org.jasig.ssp.model.tool.Tools;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.tool.PersonToolService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonToolServiceImpl
		extends AbstractAuditableCrudService<PersonTool>
		implements PersonToolService {

	@Autowired
	private transient PersonToolDao personToolsDao;

	@Override
	protected PersonToolDao getDao() {
		return personToolsDao;
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonToolServiceImpl.class);

	@Override
	public PagingWrapper<PersonTool> getAllForPerson(final Person person,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
	}

	@Override
	public PersonTool personHasTool(final Person student,
			final Tools onlyThisTool) {

		final PagingWrapper<PersonTool> wrapper = getDao()
				.getAllForPersonAndTool(
						student.getId(), onlyThisTool,
						new SortingAndPaging(ObjectStatus.ACTIVE));

		if (wrapper.getResults() == 1) {
			return wrapper.getRows().iterator().next();
		} else if (wrapper.getResults() > 1) {
			LOGGER.error("A tool has been assigned to: "
					+ student.getId().toString() + " multiple times");
			return wrapper.getRows().iterator().next();
		} else {
			return null;
		}
	}

	@Override
	public PersonTool addToolToPerson(final Person student,
			final Tools tool) {
		PersonTool personTool = personHasTool(student, tool);
		if (personTool == null) {
			personTool = new PersonTool(student, tool);
			personToolsDao.save(personTool);
		}
		return personTool;
	}

	@Override
	public PersonTool removeToolFromPerson(final Person student,
			final Tools tool) {
		final PersonTool personTool = personHasTool(student, tool);
		if (personTool != null) {
			personTool.setObjectStatus(ObjectStatus.DELETED);
			personToolsDao.save(personTool);
		}
		return personTool;
	}

	@Override
	public PersonTool save(final PersonTool obj) throws ObjectNotFoundException {
		return getDao().save(obj);
	}

}
