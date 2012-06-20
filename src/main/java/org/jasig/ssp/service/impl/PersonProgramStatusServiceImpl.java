package org.jasig.ssp.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.jasig.ssp.dao.PersonProgramStatusDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PersonProgramStatus service implementation
 * 
 * @author jon.adams
 * 
 */
@Service
public class PersonProgramStatusServiceImpl extends
		AbstractPersonAssocAuditableService<PersonProgramStatus> implements
		PersonProgramStatusService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonProgramStatusServiceImpl.class);

	@Autowired
	private transient PersonProgramStatusDao dao;

	@Override
	protected PersonProgramStatusDao getDao() {
		return dao;
	}

	@Override
	public PersonProgramStatus create(final PersonProgramStatus obj)
			throws ObjectNotFoundException, ValidationException {
		expireActive(obj.getPerson());

		try {
			return getDao().save(obj);
		} catch (final ConstraintViolationException exc) {
			throw new ValidationException(
					"Invalid data. See cause for list of violations.", exc);
		}
	}

	@Override
	public PersonProgramStatus save(final PersonProgramStatus obj)
			throws ObjectNotFoundException, ValidationException {
		// ensure expirationDate is not removed that would allow too many active
		if (obj.getExpirationDate() == null) {
			final PersonProgramStatus pps = dao.getActive(obj.getPerson());
			if (pps != null && pps.getId().equals(obj.getId())) {
				LOGGER.warn("Can not un-expire this instance while another is active. See PersonProgramStatus with ID "
						+ pps.getId());
				throw new ValidationException(
						"Can not un-expire this instance while another is active. See PersonProgramStatus with ID "
								+ pps.getId());
			}
		}

		return super.save(obj);
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final PersonProgramStatus current = getDao().get(id);

		if (!ObjectStatus.DELETED.equals(current.getObjectStatus())
				|| current.getExpirationDate() == null) {
			// Object found and is not already deleted, set it and save change.
			current.setObjectStatus(ObjectStatus.DELETED);
			current.setExpirationDate(new Date());
			try {
				save(current);
			} catch (final ValidationException exc) {
				// expirationDate is always set above, so a ValidationException
				// should never be thrown.
				LOGGER.error("ValidationException should not have occured with a delete of ID "
						+ id);
			}
		}
	}

	/**
	 * Expire the existing active PersonProgramStatus for this person, if any.
	 * 
	 * @param person
	 *            the person
	 */
	private void expireActive(final Person person) {
		final PersonProgramStatus pps = dao.getActive(person);
		if (pps != null) {
			pps.setExpirationDate(new Date());
			dao.save(pps);
		}
	}
}