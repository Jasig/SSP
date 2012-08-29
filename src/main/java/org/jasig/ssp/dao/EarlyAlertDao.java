package org.jasig.ssp.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
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

	/**
	 * Count how many open early alerts exist for the specified people
	 * (students).
	 * 
	 * <p>
	 * An 'active' means it has not been closed (has a null closedDate) and has
	 * an ObjectStatus of {@link ObjectStatus#ACTIVE}.
	 * <p>
	 * If list is empty, no results will be returned.
	 * 
	 * @param personIds
	 *            personIds for all the students for which to count early
	 *            alerts; required, but can be empty (in that case, an empty Map
	 *            will be returned)
	 * @return Map of students (personId) with the count of open early alerts
	 *         for each.
	 */
	public Map<UUID, Number> getCountOfActiveAlertsForPeopleIds(
			@NotNull final Collection<UUID> personIds) {
		// validate
		if (personIds == null) {
			throw new IllegalArgumentException(
					"Must include a collection of personIds (students).");
		}

		// setup return value
		final Map<UUID, Number> countForPeopleId = Maps.newHashMap();

		// only run the query to fill the return Map if values were given
		if (!personIds.isEmpty()) {
			// setup query
			final ProjectionList projections = Projections.projectionList();
			projections.add(Projections.groupProperty("person.id").as(
					"personId"));
			projections.add(Projections.count("id"));

			final Criteria query = createCriteria();
			query.setProjection(projections);
			query.add(Restrictions.in("person.id", personIds));
			query.add(Restrictions.isNull("closedDate"));
			query.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));

			// run query
			@SuppressWarnings("unchecked")
			final List<Object[]> results = query.list();

			// put query results into return value
			for (final Object[] result : results) {
				countForPeopleId.put((UUID) result[0], (Number) result[1]);
			}

			// ensure all people IDs that were request exist in return Map
			for (final UUID id : personIds) {
				if (!countForPeopleId.containsKey(id)) {
					countForPeopleId.put(id, 0);
				}
			}
		}

		return countForPeopleId;
	}

	public Long getEarlyAlertCountForCoach(Person coach, Date createDateFrom, Date createDateTo) {
		final Criteria query = createCriteria();

		// restrict to coach
		query.add(Restrictions.eq("createdBy", coach));

		if (createDateFrom != null) {
			query.add(Restrictions.ge("createdDate",
					createDateFrom));
		}

		if (createDateTo != null) {
			query.add(Restrictions.le("createdDate",
					createDateTo));
		}
		
		// item count
		Long totalRows = (Long) query.setProjection(Projections.rowCount())
				.uniqueResult();

		return totalRows;
	}

	public Long getStudentEarlyAlertCountForCoach(Person coach, Date createDateFrom, Date createDateTo) {
		final Criteria query = createCriteria();

		if (createDateFrom != null) {
			query.add(Restrictions.ge("createdDate",
					createDateFrom));
		}

		if (createDateTo != null) {
			query.add(Restrictions.le("createdDate",
					createDateTo));
		}
		
		Long totalRows = (Long)query.add(Restrictions.eq("createdBy", coach))
		        .setProjection(Projections.countDistinct("person")).list().get(0);

		return totalRows;
	}
}