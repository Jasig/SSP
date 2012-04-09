package edu.sinclair.mygps.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.SelfHelpGuideResponse;
import edu.sinclair.ssp.security.SspUser;

@Repository
public class SelfHelpGuideResponseDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(SelfHelpGuideResponse selfHelpGuideResponse) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(
				selfHelpGuideResponse);
	}

	public SelfHelpGuideResponse selectById(UUID selfHelpGuideResponseId) {
		return (SelfHelpGuideResponse) this.sessionFactory.getCurrentSession()
				.load(SelfHelpGuideResponse.class, selfHelpGuideResponseId);
	}

	@SuppressWarnings("unchecked")
	public List<SelfHelpGuideResponse> selectForEarlyAlert() {
		return this.sessionFactory.getCurrentSession()
				.createQuery("from SelfHelpGuideResponse shgr " +
						"where shgr.selfHelpGuide.threshold > 0 " +
						"and shgr.selfHelpGuide.threshold < " +
						"(select count(*) " +
						"from SelfHelpGuideQuestionResponse " +
						"where response = 1 " +
						"and selfHelpGuideResponse.id = shgr.id) " +
						"and shgr.person.id != ?")
				.setParameter(0, SspUser.ANONYMOUS_PERSON_ID)
				.list();
	}
}
