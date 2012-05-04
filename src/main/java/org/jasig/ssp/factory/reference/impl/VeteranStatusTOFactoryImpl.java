package org.jasig.ssp.factory.reference.impl;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.reference.VeteranStatusDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.VeteranStatusTOFactory;
import org.jasig.ssp.model.reference.VeteranStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.VeteranStatusTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * VeteranStatus transfer object factory implementation class for converting
 * back and forth from VeteranStatus models.
 * 
 * @author daniel.bower
 */
@Service
@Transactional(readOnly = true)
public class VeteranStatusTOFactoryImpl extends
		AbstractReferenceTOFactory<VeteranStatusTO, VeteranStatus>
		implements VeteranStatusTOFactory {

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	public VeteranStatusTOFactoryImpl() {
		super(VeteranStatusTO.class, VeteranStatus.class);
	}

	@Autowired
	private transient VeteranStatusDao dao;

	@Override
	protected VeteranStatusDao getDao() {
		return dao;
	}

	@Override
	public VeteranStatus from(@NotNull final VeteranStatusTO tObject)
			throws ObjectNotFoundException {
		final VeteranStatus model = super.from(tObject);

		model.setSortOrder(tObject.getSortOrder());

		return model;
	}
}
