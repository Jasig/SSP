package org.jasig.ssp.dao;

import java.util.Collection;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRestrictedPersonAssocAuditableCrudDao<T extends RestrictedPersonAssocAuditable>
		extends AbstractPersonAssocAuditableCrudDao<T>
		implements RestrictedPersonAssocAuditableDao<T> {

	protected AbstractRestrictedPersonAssocAuditableCrudDao(
			final Class<T> persistentClass) {
		super(persistentClass);
	}

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractRestrictedPersonAssocAuditableCrudDao.class);

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	protected void addConfidentialityLevelsRestriction(
			final SspUser requestor,
			final Criteria criteria) {

		final Collection<ConfidentialityLevel> levels = confidentialityLevelService
				.filterConfidentialityLevelsFromGrantedAuthorities(requestor
						.getAuthorities());

		if (levels.isEmpty()) {
			criteria.add(
					Restrictions.eq("createdBy.id", requestor.getPerson()
							.getId()));
		} else {
			criteria.add(Restrictions.or(
					Restrictions.in("confidentialityLevel", levels),
					Restrictions.eq("createdBy.id", requestor.getPerson()
							.getId())));
			LOGGER.debug("Number of Confidentiality Levels for user {}",
					levels.size());
		}
	}

	@Override
	public PagingWrapper<T> getAllForPersonId(final UUID personId,
			final SspUser requestor,
			final SortingAndPaging sAndP) {

		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));

		addConfidentialityLevelsRestriction(requestor, criteria);

		return processCriteriaWithPaging(criteria, sAndP);
	}

	/**
	 * this method will throw UnsupportedOperationException. Use
	 * getAllForPersonId with personId, requestor and sAndP instead.
	 */
	@Override
	public PagingWrapper<T> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {
		throw new UnsupportedOperationException(
				"For Restricted Person Associated Auditables, you must call getAllForPersonId and supply a requestor");
	}
}
