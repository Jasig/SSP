package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.ReferralDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.Referral;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.ReferralService;

@Service
@Transactional
public class ReferralServiceImpl implements ReferralService {

	@Autowired
	transient private ReferralDao dao;

	@Override
	public List<Referral> getAll(final ObjectStatus status,
			final Integer firstResult, final Integer maxResults,
			final String sort, final String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public Referral get(final UUID id) throws ObjectNotFoundException {
		final Referral obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "Referral");
		}

		return obj;
	}

	@Override
	public Referral create(final Referral obj) {
		return dao.save(obj);
	}

	@Override
	public Referral save(final Referral obj) throws ObjectNotFoundException {
		Referral current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		Referral current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(final ReferralDao dao) {
		this.dao = dao;
	}
}
