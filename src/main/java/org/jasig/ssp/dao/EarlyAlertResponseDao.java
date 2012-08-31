package org.jasig.ssp.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * EarlyAlertResponse data access methods
 * 
 * @author jon.adams
 * 
 */
@Repository
public class EarlyAlertResponseDao extends
		AbstractAuditableCrudDao<EarlyAlertResponse> implements
		AuditableCrudDao<EarlyAlertResponse> {

	/**
	 * Construct a data access instance with specific class types for use by
	 * super class methods.
	 */
	protected EarlyAlertResponseDao() {
		super(EarlyAlertResponse.class);
	}

	/**
	 * Get all {@link EarlyAlertResponse} for the specified {@link EarlyAlert}.
	 * 
	 * @param earlyAlertId
	 *            Early Alert identifier
	 * @param sAndP
	 *            Sorting and paging filters
	 * @return All EarlyAlertResponses for the specified person.
	 */
	public PagingWrapper<EarlyAlertResponse> getAllForEarlyAlertId(
			final UUID earlyAlertId,
			final SortingAndPaging sAndP) {
		return processCriteriaWithSortingAndPaging(
				createCriteria().add(
						Restrictions.eq("earlyAlert.id", earlyAlertId)), sAndP,
				true);
	}

	public Long getEarlyAlertResponseCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {

		final Criteria query = createCriteria();
 
		
		/*
		 * 
		 * Criteria criteria = session.createCriteria(Student.class)
    .createAlias("courses", "course")
    .createAlias("course.group", "student")
    .add(Restrictions.eq("course.name", "Math"))
    .add(Restrictions.eq("student.name", "John"));
		 */
		
		
		
		// add possible studentTypeId Check
		if (studentTypeIds != null && !studentTypeIds.isEmpty()) {
			//.createAlias("person",
			//		"person")
			query.createAlias("earlyAlert",	"earlyAlert").createAlias("earlyAlert.person", "student").createAlias("student.studentType","studentType").add(
						Restrictions.in("studentType.id",studentTypeIds));
					
		}		

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
}