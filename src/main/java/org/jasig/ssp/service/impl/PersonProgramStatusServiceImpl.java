/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.PersonProgramStatusDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient ProgramStatusService programStatusService;

	@Override
	protected PersonProgramStatusDao getDao() {
		return dao;
	}

	@Override
	public PersonProgramStatus create(
			final PersonProgramStatus personProgramStatus)
			throws ObjectNotFoundException, ValidationException {
		expireActive(personProgramStatus.getPerson(), personProgramStatus);

		try {
			return getDao().save(personProgramStatus);
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
			final PersonProgramStatus pps = getActiveExcluding(obj.getPerson(), obj);
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

		if (!ObjectStatus.INACTIVE.equals(current.getObjectStatus())
				|| current.getExpirationDate() == null) {
			// Object found and is not already deleted, set it and save change.
			current.setObjectStatus(ObjectStatus.INACTIVE);
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
	 * @param savingStatus
	 *            the status association currently being saved
	 * @throws ValidationException if the current list of statuses is in an
	 *   invalid state
	 */
	@Override
	public void expireActive(final Person person, PersonProgramStatus savingStatus)
	throws ValidationException {
		PersonProgramStatus pps = getActiveExcluding(person, savingStatus);
		if ( pps != null ) {
			pps.setExpirationDate(new Date());
			// make sure this runs before a subsequent insert (if any), else
			// the next Hib session flush might run the insert before the
			// update and we'd get two active statuses at the same time,
			// which will break db triggers
			dao.saveAndFlush(pps);
		}
	}

	private PersonProgramStatus getActiveExcluding(final Person forPerson,
												   final PersonProgramStatus exclude)
			throws ValidationException {
		return getActiveExcluding(forPerson.getId(), exclude);
	}

	private PersonProgramStatus getActiveExcluding(final UUID forPersonId,
												   final PersonProgramStatus exclude)
			throws ValidationException {
		// Cannot just ask dao for a single active record b/c the status
		// currently being saved might be flushed to the db as a side-effect
		// of the dao call. Have to pull everything back that's not expired,
		// then filter out the status we're currently working on.
		final List<PersonProgramStatus> active = dao.getActive(forPersonId);
		if ( active == null || active.isEmpty() ) {
			return null;
		}
		if ( exclude != null ) {
			active.remove(exclude);
		}
		if ( active.isEmpty() ) {
			return null;
		}
		if ( active.size() > 1 ) {
			throw new ValidationException(
					"More than one unexpired ProgramStatus for person '"
							+ forPersonId + "'. Unable to determine"
							+ " which of those to expire.");
		}
		return active.iterator().next();
	}


	@Override
	public PersonProgramStatus getCurrent(final UUID personId)
			throws ObjectNotFoundException, ValidationException {
		return getActiveExcluding(personId, null);
	}

	@Override
	public void setTransitionForStudent(@NotNull final Person person)
			throws ObjectNotFoundException, ValidationException {
		final ProgramStatus transitioned = programStatusService
				.get(ProgramStatus.TRANSITIONED_ID);

		// check if transition needs done
		final PersonProgramStatus current = getCurrent(person.getId());
		if (current != null && transitioned.equals(current.getProgramStatus())) {
			// current status is already "Transitioned" - nothing to be done
			return;
		}

		final PersonProgramStatus ps = new PersonProgramStatus();
		ps.setEffectiveDate(new Date());
		ps.setPerson(person);
		ps.setProgramStatus(transitioned);
		ps.setProgramStatusChangeReason(null);
		create(ps);
	}
}