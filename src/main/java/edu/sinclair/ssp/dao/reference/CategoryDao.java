package edu.sinclair.ssp.dao.reference;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Category;

/**
 * Data access class for the Category reference entity.
 */
@Repository
public class CategoryDao extends ReferenceAuditableCrudDao<Category> implements
		AuditableCrudDao<Category> {

	public CategoryDao() {
		super(Category.class);
	}

	@SuppressWarnings("unchecked")
	public List<Category> selectAffirmativeBySelfHelpGuideResponseId(
			final UUID selfHelpGuideResponseId) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select distinct c "
								+ "from Category c "
								+ "inner join c.selfHelpGuideQuestions shgq "
								+ "inner join shgq.selfHelpGuideQuestionResponses shgqr "
								+ "where shgqr.response = 1 "
								+ "and shgqr.selfHelpGuideResponse.id = ? "
								+ "order by c.name")
				.setParameter(0, selfHelpGuideResponseId).list();
	}

	@SuppressWarnings("unchecked")
	public List<Category> searchByQuery(String query) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select distinct c "
								+ "from Category c "
								+ "inner join c.categoryCategoryReferrals ccr "
								+ "where c.objectStatus.id = :objectStatusId "
								+ "and c.showInSelfHelpSearch = true "
								+ "and (upper(c.name) like :query "
								+ "or upper(c.selfHelpGuideQuestion) like :query "
								+ "or upper(c.selfHelpGuideDescription) like :query "
								+ "or upper(c.tags) like :query) "
								+ "and exists " + "(from CategoryReferral "
								+ "where id = ccr.categoryReferral.id "
								+ "and showInSelfHelpGuide = true "
								+ "and objectStatus.id = :objectStatusId) "
								+ "order by c.name")
				.setParameter("query",
						"%" + query.toUpperCase(Locale.getDefault()) + "%")
				.setParameter("objectStatusId", ObjectStatus.ACTIVE).list();

	}

	@SuppressWarnings("unchecked")
	public List<Category> selectForStudentIntake() {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Category " + "where showInStudentIntake = true "
								+ "order by name").list();
	}

}
