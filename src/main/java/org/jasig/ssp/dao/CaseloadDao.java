package org.jasig.ssp.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

@Repository
public class CaseloadDao extends AbstractDao<Person> {

	public CaseloadDao() {
		super(Person.class);
	}

	@SuppressWarnings("unchecked")
	public PagingWrapper<CaseloadRecord> caseLoadFor(
			final ProgramStatus programStatus,
			final Person coach, final SortingAndPaging sAndP) {

		final Criteria query = createCriteria();

		// Restrict by program status if provided
		if (programStatus != null) {
			query.createAlias("programStatuses", "personProgramStatus")
					.add(Restrictions.eq("personProgramStatus.programStatus",
							programStatus));
		}

		// Set Columns to Return: id, firstName, middleInitial, lastName,
		// schoolId
		final ProjectionList projections = Projections.projectionList();
		projections.add(Projections.groupProperty("id").as("personId"));
		projections.add(Projections.groupProperty("firstName").as("firstName"));
		projections.add(Projections.groupProperty("middleInitial").as(
				"middleInitial"));
		projections.add(Projections.groupProperty("lastName").as("lastName"));
		projections.add(Projections.groupProperty("schoolId").as("schoolId"));
		projections.add(Projections.groupProperty("studentIntakeCompleteDate")
				.as(
						"studentIntakeCompleteDate"));

		// Join to Student Type
		query.createAlias("studentType", "studentType",
				JoinType.LEFT_OUTER_JOIN);
		// add StudentTypeName Column
		projections.add(Projections.groupProperty("studentType.name").as(
				"studentTypeName"));

		// Join to EarlyAlert
		query.createAlias("earlyAlerts", "earlyAlert", JoinType.LEFT_OUTER_JOIN);
		// but only look at earlyAlerts that haven't been closed
		query.add(Restrictions.isNull("earlyAlert.closedDate"));
		// Add numberOfEarlyalerts Column
		projections.add(Projections.count("earlyAlert.id").as(
				"numberOfEarlyAlerts"));

		// :TODO current AppointmentDate for Caseload
		// projections.add(Projections.property("currentAppointmentDate").as("currentAppointmentDate"));

		query.setProjection(projections);

		query.add(Restrictions.eq("coach", coach));

		query.setResultTransformer(new AliasToBeanResultTransformer(
				CaseloadRecord.class));

		return new PagingWrapper<CaseloadRecord>(0, query.list());
	}
}
