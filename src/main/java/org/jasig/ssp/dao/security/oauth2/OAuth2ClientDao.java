package org.jasig.ssp.dao.security.oauth2;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AbstractAuditableCrudDao;
import org.jasig.ssp.model.security.oauth2.OAuth2Client;
import org.springframework.stereotype.Repository;

@Repository
public class OAuth2ClientDao extends AbstractAuditableCrudDao<OAuth2Client> {

	protected OAuth2ClientDao() {
		super(OAuth2Client.class);
	}

	public OAuth2Client findByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("username can not be empty.");
		}

		final Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(OAuth2Client.class);
		query.add(Restrictions.eq("username", username));
		return (OAuth2Client) query.uniqueResult();
	}

}
