package org.jasig.ssp.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public class JournalEntryDao
		extends AbstractRestrictedPersonAssocAuditableCrudDao<JournalEntry>
		implements RestrictedPersonAssocAuditableDao<JournalEntry> {

	protected JournalEntryDao() {
		super(JournalEntry.class);
	}

	public Long getJournalCountForCoach(Person coach, Date createDateFrom, Date createDateTo) {

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

	public Long getStudentJournalCountForCoach(Person coach, Date createDateFrom, Date createDateTo) {

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
