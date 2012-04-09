package edu.sinclair.mygps.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.SelfHelpGuideQuestionResponse;
import edu.sinclair.ssp.security.SspUser;

@Repository
public class SelfHelpGuideQuestionResponseDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(SelfHelpGuideQuestionResponse selfHelpGuideQuestionResponse) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(
				selfHelpGuideQuestionResponse);
	}

	@SuppressWarnings("unchecked")
	public List<SelfHelpGuideQuestionResponse> selectCriticalResponsesForEarlyAlert() {

		return this.sessionFactory.getCurrentSession()
				.createQuery("from SelfHelpGuideQuestionResponse " +
						"where earlyAlertSent = false " +
						"and response = true " +
						"and selfHelpGuideQuestion.critical = true " +
						"and selfHelpGuideResponse.person.id != ?")
				.setParameter(0, SspUser.ANONYMOUS_PERSON_ID)
				.list();

	}
}
