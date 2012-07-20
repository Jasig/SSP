package org.jasig.ssp.dao;

import java.util.Date;

import javax.validation.constraints.NotNull;

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
			final ProgramStatus programStatus, @NotNull final Person coach,
			final SortingAndPaging sAndP) {

		// This creation of the query is order sensitive as 2 queries are run
		// with the same restrictions. The first query simply runs the query to
		// find a count of the records. The second query returns the row data.

		final Criteria query = createCriteria();

		// Restrict by program status if provided
		if (programStatus != null) {
			final Criteria subquery = query.createAlias("programStatuses",
					"personProgramStatus");
			subquery.add(
					Restrictions.or(
							Restrictions
									.isNull("personProgramStatus.expirationDate"),
							Restrictions.ge(
									"personProgramStatus.expirationDate",
									new Date())));
			subquery.add(Restrictions.eq("personProgramStatus.programStatus",
					programStatus));
		}

		// restrict to coach
		query.add(Restrictions.eq("coach", coach));

		// item count
		Long totalRows = 0L;
		if ((sAndP != null) && sAndP.isPaged()) {
			totalRows = (Long) query.setProjection(Projections.rowCount())
					.uniqueResult();
		}

		// clear the row count projection
		query.setProjection(null);

		//
		// Add Properties to return in the case load
		//
		// Set Columns to Return: id, firstName, middleInitial, lastName,
		// schoolId
		final ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id").as("personId"));
		projections.add(Projections.property("firstName").as("firstName"));
		projections.add(Projections.property("middleInitial").as(
				"middleInitial"));
		projections.add(Projections.property("lastName").as("lastName"));
		projections.add(Projections.property("schoolId").as("schoolId"));
		projections.add(Projections.property("studentIntakeCompleteDate")
				.as(
						"studentIntakeCompleteDate"));

		// Join to Student Type
		query.createAlias("studentType", "studentType",
				JoinType.LEFT_OUTER_JOIN);
		// add StudentTypeName Column
		projections.add(Projections.property("studentType.name").as(
				"studentTypeName"));

		query.setProjection(projections);

		query.setResultTransformer(new AliasToBeanResultTransformer(
				CaseloadRecord.class));

		// Add Paging
		if (sAndP != null) {
			sAndP.addAll(query);
		}

		return new PagingWrapper<CaseloadRecord>(totalRows, query.list());
	}
}