package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.ConfidentialityDisclosureAgreementDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.ConfidentialityDisclosureAgreementService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
public class ConfidentialityDisclosureAgreementServiceImpl implements ConfidentialityDisclosureAgreementService {

	@Autowired
	private ConfidentialityDisclosureAgreementDao dao;

	@Override
	public List<ConfidentialityDisclosureAgreement> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public ConfidentialityDisclosureAgreement get(UUID id) throws ObjectNotFoundException {
		ConfidentialityDisclosureAgreement obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "ConfidentialityDisclosureAgreement");
		}

		return obj;
	}

	@Override
	public ConfidentialityDisclosureAgreement create(ConfidentialityDisclosureAgreement obj) {
		return dao.save(obj);
	}

	@Override
	public ConfidentialityDisclosureAgreement save(ConfidentialityDisclosureAgreement obj) throws ObjectNotFoundException {
		ConfidentialityDisclosureAgreement current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		ConfidentialityDisclosureAgreement current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(ConfidentialityDisclosureAgreementDao dao) {
		this.dao = dao;
	}
}
