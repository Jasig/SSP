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

		query.add(Restrictions.eq("coach", coach));

		if (programStatus != null) {
			query.createAlias("programStatuses", "personProgramStatus")
					.add(Restrictions.eq("personProgramStatus.programStatus",
							programStatus));
		}

		query.createAlias("studentType", "studentType",
				JoinType.LEFT_OUTER_JOIN);

		final ProjectionList projections = Projections.projectionList();
		projections.add(Projections.id().as("personId"));
		projections.add(Projections.property("firstName").as("firstName"));
		projections.add(Projections.property("middleInitial").as(
				"middleInitial"));
		projections.add(Projections.property("lastName").as("lastName"));
		projections.add(Projections.property("schoolId").as("schoolId"));
		projections.add(Projections.property("studentType.name").as(
				"studentTypeName"));
		// :TODO current AppointmentDate for Caseload
		// projections.add(Projections.property("currentAppointmentDate").as("currentAppointmentDate"));
		// :TODO studentIntakeComplete for Caseload
		// projections.add(Projections.property("studentIntakeComplete").as(
		// "studentIntakeComplete"));
		query.setProjection(projections);

		query.setResultTransformer(new AliasToBeanResultTransformer(
				CaseloadRecord.class));

		return new PagingWrapper<CaseloadRecord>(0, query.list());
	}
}
