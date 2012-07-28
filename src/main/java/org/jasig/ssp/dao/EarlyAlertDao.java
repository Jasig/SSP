package org.jasig.ssp.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.EarlyAlert;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;

/**
 * EarlyAlert data access methods
 * 
 * @author jon.adams
 * 
 */
@Repository
public class EarlyAlertDao extends
		AbstractPersonAssocAuditableCrudDao<EarlyAlert> implements
		PersonAssocAuditableCrudDao<EarlyAlert> {

	/**
	 * Construct a data access instance with specific class types for use by
	 * super class methods.
	 */
	protected EarlyAlertDao() {
		super(EarlyAlert.class);
	}

	public Map<UUID, Number> getCountOfActiveAlertsForPeopleIds(
			final Collection<UUID> peopleIds) {

		final Map<UUID, Number> countForPeopleId = Maps.newHashMap();

		final Criteria query = createCriteria();

		final ProjectionList projections = Projections.projectionList();
		projections.add(Projections.groupProperty("person.id").as("personid"));
		projections.add(Projections.count("id"));
		query.setProjection(projections);

		query.add(Restrictions.isNull("closedDate"));

		@SuppressWarnings("unchecked")
		final List<Object[]> results = query.list();
		for (Object[] result : results) {
			countForPeopleId.put((UUID) result[0], (Number) result[1]);
		}
		return countForPeopleId;
	}
}
